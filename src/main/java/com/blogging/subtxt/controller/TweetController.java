package com.blogging.subtxt.controller;

import com.blogging.subtxt.dto.request.TweetRequest;
import com.blogging.subtxt.dto.responses.TweetResponse;
import com.blogging.subtxt.services.TweetServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
@RequiredArgsConstructor
public class TweetController {

    private final TweetServices tweetServices;

    @PostMapping
    public ResponseEntity<TweetResponse> createTweet(
            @Valid @RequestBody TweetRequest request,
            Authentication authentication) {
        TweetResponse response = tweetServices.createTweet(request, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{tweetId}")
    public ResponseEntity<TweetResponse> updateTweet(
            @PathVariable Long tweetId,
            @Valid @RequestBody TweetRequest request,
            Authentication authentication) {
        TweetResponse response = tweetServices.updateTweet(tweetId, request, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{tweetId}")
    public ResponseEntity<Void> deleteTweet(
            @PathVariable Long tweetId,
            Authentication authentication) {
        tweetServices.deleteTweet(tweetId, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{tweetId}")
    public ResponseEntity<TweetResponse> getTweetById(@PathVariable Long tweetId) {
        return ResponseEntity.ok(tweetServices.getTweetById(tweetId));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<TweetResponse>> getTweetsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(tweetServices.getTweetsByUsername(username));
    }

    @GetMapping("/user/{username}/paginated")
    public ResponseEntity<Page<TweetResponse>> getTweetsByUsernamePaginated(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(tweetServices.getTweetsByUsernamePaginated(username, pageable));
    }

    @GetMapping("/feed")
    public ResponseEntity<Page<TweetResponse>> getUserFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        return ResponseEntity.ok(tweetServices.getUserFeed(authentication.getName(), page, size));
    }

    @GetMapping("/recent")
    public ResponseEntity<Page<TweetResponse>> getRecentTweets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(tweetServices.getRecentTweets(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TweetResponse>> searchTweets(@RequestParam String query) {
        return ResponseEntity.ok(tweetServices.searchTweets(query));
    }

    // ✅ New: Get Tweets by Hashtag
    @GetMapping("/hashtag/{tag}")
    public ResponseEntity<List<TweetResponse>> getTweetsByHashtag(@PathVariable String tag) {
        return ResponseEntity.ok(tweetServices.getTweetsByHashtag(tag));
    }
    //get all without paginated
    @GetMapping("/all")
    public ResponseEntity<List<TweetResponse>> getAllTweets() {
        return ResponseEntity.ok(tweetServices.getAllTweets());
    }
}
