package com.blogging.subtxt.services.impl;

import com.blogging.subtxt.dto.responses.LikeResponse;
import com.blogging.subtxt.models.Like;
import com.blogging.subtxt.models.Tweet;
import com.blogging.subtxt.models.User;
import com.blogging.subtxt.repository.LikeRepository;
import com.blogging.subtxt.repository.TweetRepository;
import com.blogging.subtxt.repository.UserRepository;
import com.blogging.subtxt.services.LikeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    @Override
    @Transactional
    public LikeResponse toggleLike(Long tweetId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + userEmail));

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new EntityNotFoundException("Tweet not found with ID: " + tweetId));

        boolean liked;

        var existingLike = likeRepository.findByTweetAndUser(tweet, user);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            liked = false;
        } else {
            Like newLike = new Like();
            newLike.setTweet(tweet);
            newLike.setUser(user);
            likeRepository.save(newLike);
            liked = true;
        }

        long totalLikes = likeRepository.countByTweet(tweet);

        return new LikeResponse(
                tweet.getId(),
                user.getId(),
                user.getUsername(),
                liked,
                totalLikes
        );
    }

    @Override
    public long countLikesForTweet(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new EntityNotFoundException("Tweet not found with ID: " + tweetId));
        return likeRepository.countByTweet(tweet);
    }

    @Override
    public boolean isTweetLikedByUser(Long tweetId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + userEmail));

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new EntityNotFoundException("Tweet not found with ID: " + tweetId));

        return likeRepository.existsByTweetAndUser(tweet, user);
    }
}
