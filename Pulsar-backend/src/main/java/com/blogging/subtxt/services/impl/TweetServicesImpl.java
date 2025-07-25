package com.blogging.subtxt.services.impl;

import com.blogging.subtxt.dto.request.TweetRequest;
import com.blogging.subtxt.dto.responses.TweetResponse;
import com.blogging.subtxt.exception.ResourceNotFoundException;
import com.blogging.subtxt.models.Tweet;
import com.blogging.subtxt.models.User;
import com.blogging.subtxt.repository.TweetRepository;
import com.blogging.subtxt.repository.UserRepository;
import com.blogging.subtxt.services.TweetServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TweetServicesImpl implements TweetServices {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    // CRUD Service
    @Override
    public TweetResponse createTweet(TweetRequest tweetRequest, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Tweet tweet = new Tweet();
        tweet.setContent(tweetRequest.getContent());
        tweet.setUser(user);

        Tweet savedTweet = tweetRepository.save(tweet);
        return mapToResponse(savedTweet);
    }

    @Override
    public TweetResponse updateTweet(Long tweetId, TweetRequest tweetRequest, String username) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet not found"));

        if (!tweet.getUser().getEmail().equals(username)) {
            throw new RuntimeException("Unauthorized to update this tweet");
        }

        tweet.setContent(tweetRequest.getContent());
        Tweet updated = tweetRepository.save(tweet);
        return mapToResponse(updated);
    }

    @Override
    public void deleteTweet(Long tweetId, String username) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet not found"));

        if (!tweet.getUser().getEmail().equals(username)) {
            throw new RuntimeException("Unauthorized to delete this tweet");
        }

        tweetRepository.delete(tweet);
    }

    @Override
    public TweetResponse getTweetById(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet not found by this id"));
        return mapToResponse(tweet);
    }

    @Override
    public List<TweetResponse> getAllTweets() {
        return tweetRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TweetResponse> getTweetsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Tweet> tweets = tweetRepository.findByUser(user);
        return tweets.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Following User Recent Tweet
    @Override
    public Page<TweetResponse> getUserFeed(String username, int page, int size) {
        User currentUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Page<Tweet> tweets = tweetRepository.findHomeFeedTweets(currentUser.getId(), PageRequest.of(page, size));
        return tweets.map(this::mapToResponse);
    }

    // All User Recent Tweet
    @Override
    public Page<TweetResponse> getRecentTweets(Pageable pageable) {
        return tweetRepository.findAllRecentTweets(pageable)
                .map(this::mapToResponse);
    }

    // Search Tweet By Keyword
    @Override
    public List<TweetResponse> searchTweets(String query) {
        return tweetRepository.findByContentContainingIgnoreCase(query)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TweetResponse mapToResponse(Tweet tweet) {
        TweetResponse response = new TweetResponse();
        response.setId(tweet.getId());
        response.setContent(tweet.getContent());
        response.setCreatedDate(tweet.getCreatedDate());
        response.setUpdatedDate(tweet.getUpdatedDate());
        response.setUsername(tweet.getUser().getUsername());
        return response;
    }
}
