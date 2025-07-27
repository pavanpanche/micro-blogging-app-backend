package com.blogging.subtxt.services;

import com.blogging.subtxt.dto.responses.LikeResponse;

public interface LikeService {
    LikeResponse toggleLike(Long tweetId, String email);
    long countLikesForTweet(Long tweetId);
    boolean isTweetLikedByUser(Long tweetId, String email);
}
