package com.blogging.subtxt.controller;

import com.blogging.subtxt.dto.request.TweetRequest;
import com.blogging.subtxt.dto.responses.TweetResponse;
import com.blogging.subtxt.services.TweetServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tweets")
@RequiredArgsConstructor
public class TweetController {

    private final TweetServices tweetServices;

    // CRUD Endpoint
    @PostMapping
    public ResponseEntity<TweetResponse> createTweet(@Valid @RequestBody TweetRequest request, Authentication authentication) {
        TweetResponse response = tweetServices.createTweet(request, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TweetResponse> updateTweet(@PathVariable Long id, @Valid @RequestBody TweetRequest request, Authentication authentication)
    {
        TweetResponse response = tweetServices.updateTweet(id, request, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTweet(@PathVariable Long id, Authentication authentication) {
        tweetServices.deleteTweet(id, authentication.getName());
        return ResponseEntity.ok("Tweet deleted successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetResponse> getTweetById(@PathVariable Long id) {
        return ResponseEntity.ok(tweetServices.getTweetById(id));
    }

    @GetMapping
    public ResponseEntity<List<TweetResponse>> getAllTweets() {
        return ResponseEntity.ok(tweetServices.getAllTweets());
    }


    //  Fetch paginated home feed (tweets by user + followings)
    @GetMapping("/feed")
    public ResponseEntity<Page<TweetResponse>> getUserFeed(Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TweetResponse> feed = tweetServices.getUserFeed(authentication.getName(), page, size);
        return ResponseEntity.ok(feed);
    }

    // All User Recent Tweet  it will display on dedicated section on home screen
    @GetMapping("/recent")
    public ResponseEntity<Page<TweetResponse>> getRecentTweets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<TweetResponse> recentTweets = tweetServices.getRecentTweets(pageable);
        return ResponseEntity.ok(recentTweets);
    }

    // find tweet by User username
    @GetMapping("/tweets/user/{username}")
    public ResponseEntity<List<TweetResponse>> getTweetsByUser(@PathVariable String username) {
        List<TweetResponse> tweets = tweetServices.getTweetsByUsername(username);
        return ResponseEntity.ok(tweets);
    }
    //Search tweet by keyword and hashtag  it will on search bar
    @GetMapping("/search")
    public ResponseEntity<List<TweetResponse>> searchTweets(@RequestParam String query) {
        return ResponseEntity.ok(tweetServices.searchTweets(query));
    }


}
