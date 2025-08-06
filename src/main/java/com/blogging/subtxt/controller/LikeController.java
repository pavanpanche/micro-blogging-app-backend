package com.blogging.subtxt.controller;

import com.blogging.subtxt.dto.responses.LikeResponse;
import com.blogging.subtxt.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/toggle/{tweetId}")
    public ResponseEntity<LikeResponse> toggleLike(@PathVariable Long tweetId, Principal principal) {
        LikeResponse response = likeService.toggleLike(tweetId, principal.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count/{tweetId}")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long tweetId) {
        long count = likeService.countLikesForTweet(tweetId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/status/{tweetId}")
    public ResponseEntity<Boolean> isLiked(@PathVariable Long tweetId, Principal principal) {
        boolean liked = likeService.isTweetLikedByUser(tweetId, principal.getName());
        return ResponseEntity.ok(liked);
    }



}
