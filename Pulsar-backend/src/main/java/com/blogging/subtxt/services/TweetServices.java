package com.blogging.subtxt.services;

import com.blogging.subtxt.dto.request.TweetRequest;
import com.blogging.subtxt.dto.responses.TweetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TweetServices {
    TweetResponse createTweet(TweetRequest tweetRequest, String username);
    TweetResponse updateTweet(Long tweetId, TweetRequest tweetRequest, String username);
    void deleteTweet(Long tweetId, String username);
    TweetResponse getTweetById(Long tweetId);
    List<TweetResponse> getAllTweets();
    List<TweetResponse> getTweetsByUsername(String username);
    Page<TweetResponse> getUserFeed(String username, int page, int size);
    Page<TweetResponse> getRecentTweets(Pageable pageable);
    List<TweetResponse> searchTweets(String query);


}
