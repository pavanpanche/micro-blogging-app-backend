package com.blogging.subtxt.controller;
import com.blogging.subtxt.models.User;
import com.blogging.subtxt.services.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{username}")
    public ResponseEntity<String> followUser(@PathVariable String username, Principal principal) {
        if (principal.getName().equals(username)) {
            return ResponseEntity.badRequest().body("You cannot follow yourself");
        }
        followService.followUser(principal.getName(), username);
        return ResponseEntity.ok("Now following " + username);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> unfollowUser(@PathVariable String username, Principal principal) {
        followService.unfollowUser(principal.getName(), username);
        return ResponseEntity.ok("Unfollowed " + username);
    }

    @GetMapping("/followers/{username}")
    public ResponseEntity<List<String>> getFollowers(@PathVariable String username) {
        List<String> followers = followService.getFollowers(username)
                .stream()
                .map(User::getUsername)
                .toList();
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following/{username}")
    public ResponseEntity<List<String>> getFollowing(@PathVariable String username) {
        List<String> following = followService.getFollowing(username)
                .stream()
                .map(User::getUsername)
                .toList();
        return ResponseEntity.ok(following);
    }

    @GetMapping("/count/{username}")
    public ResponseEntity<Map<String, Long>> getCounts(@PathVariable String username) {
        long followers = followService.getFollowerCount(username);
        long following = followService.getFollowingCount(username);
        return ResponseEntity.ok(Map.of(
                "followers", followers,
                "following", following
        ));
    }

    @GetMapping("/is-following/{username}")
    public ResponseEntity<Boolean> isFollowing(@PathVariable String username, Principal principal) {
        boolean status = followService.isFollowing(principal.getName(), username);
        return ResponseEntity.ok(status);
    }
}
