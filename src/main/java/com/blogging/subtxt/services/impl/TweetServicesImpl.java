package com.blogging.subtxt.services.impl;

import com.blogging.subtxt.dto.request.TweetRequest;
import com.blogging.subtxt.dto.responses.TweetResponse;
import com.blogging.subtxt.exception.ResourceNotFoundException;
import com.blogging.subtxt.models.Hashtag;
import com.blogging.subtxt.models.Tweet;
import com.blogging.subtxt.models.User;
import com.blogging.subtxt.repository.HashtagRepository;
import com.blogging.subtxt.repository.TweetRepository;
import com.blogging.subtxt.repository.UserRepository;
import com.blogging.subtxt.services.LikeService;
import com.blogging.subtxt.services.TweetServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TweetServicesImpl implements TweetServices {

    private final TweetRepository tweetRepository;
    private final HashtagRepository hashtagRepository;
    private final UserRepository userRepository;
    private final LikeService likeService;

    @Override
    public TweetResponse createTweet(TweetRequest tweetRequest, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        Tweet tweet = new Tweet();
        tweet.setContent(tweetRequest.getContent());
        tweet.setUser(user);

        Set<Hashtag> hashtags = extractHashtags(tweetRequest.getContent());
        tweet.setHashtags(hashtags);

        Tweet savedTweet = tweetRepository.save(tweet);
        return mapToResponse(savedTweet, email);
    }

    @Override
    public TweetResponse updateTweet(Long tweetId, TweetRequest tweetRequest, String email) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet not found with id: " + tweetId));

        if (!tweet.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized to update this tweet.");
        }

        tweet.setContent(tweetRequest.getContent());
        tweet.setHashtags(extractHashtags(tweetRequest.getContent()));

        Tweet updatedTweet = tweetRepository.save(tweet);
        return mapToResponse(updatedTweet, email);
    }

    @Override
    public void deleteTweet(Long tweetId, String email) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet not found with id: " + tweetId));

        if (!tweet.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized to delete this tweet.");
        }

        tweetRepository.delete(tweet);
    }

    @Override
    public TweetResponse getTweetById(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet not found with id: " + tweetId));
        return mapToResponse(tweet, tweet.getUser().getEmail());
    }

    @Override
    public List<TweetResponse> getAllTweets() {
        return tweetRepository.findAll().stream()
                .map(tweet -> mapToResponse(tweet, tweet.getUser().getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TweetResponse> getTweetsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        return tweetRepository.findByUser(user).stream()
                .map(tweet -> mapToResponse(tweet, user.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public Page<TweetResponse> getTweetsByUsernamePaginated(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        return tweetRepository.findByUser(user, pageable)
                .map(tweet -> mapToResponse(tweet, user.getEmail()));
    }

    @Override
    public Page<TweetResponse> getUserFeed(String email, int page, int size) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return tweetRepository.findHomeFeedTweets(user.getId(), PageRequest.of(page, size))
                .map(tweet -> mapToResponse(tweet, email));
    }

    @Override
    public Page<TweetResponse> getRecentTweets(Pageable pageable) {
        return tweetRepository.findAllRecentTweets(pageable)
                .map(tweet -> mapToResponse(tweet, tweet.getUser().getEmail()));
    }

    @Override
    public List<TweetResponse> searchTweets(String query) {
        return tweetRepository.findByContentContainingIgnoreCase(query).stream()
                .map(tweet -> mapToResponse(tweet, tweet.getUser().getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TweetResponse> getTweetsByHashtag(String tag) {
        return tweetRepository.findByHashtags_TagIgnoreCase(tag.toLowerCase()).stream()
                .sorted(Comparator.comparing(Tweet::getCreatedAt).reversed())
                .map(tweet -> mapToResponse(tweet, tweet.getUser().getEmail()))
                .collect(Collectors.toList());
    }

    private Set<Hashtag> extractHashtags(String content) {
        Set<Hashtag> hashtags = new HashSet<>();
        if (content == null || content.isBlank()) return hashtags;

        Pattern pattern = Pattern.compile("#(\\w+)");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String tag = matcher.group(1).toLowerCase();

            Hashtag hashtag = hashtagRepository.findByTag(tag)
                    .orElseGet(() -> hashtagRepository.save(new Hashtag(tag)));

            hashtags.add(hashtag);
        }
        return hashtags;
    }

    private TweetResponse mapToResponse(Tweet tweet, String email) {
        return TweetResponse.builder()
                .id(tweet.getId())
                .content(tweet.getContent())
                .createdAt(tweet.getCreatedAt())
                .updatedAt(tweet.getUpdatedAt())
                .username(tweet.getUser() != null ? tweet.getUser().getUsername() : "unknown")
                .likeCount(likeService.countLikesForTweet(tweet.getId()))
                .isLiked(likeService.isTweetLikedByUser(tweet.getId(), email))
                .hashtags(tweet.getHashtags() != null
                        ? tweet.getHashtags().stream().map(Hashtag::getTag).collect(Collectors.toSet())
                        : Set.of())
                .build();
    }
}
