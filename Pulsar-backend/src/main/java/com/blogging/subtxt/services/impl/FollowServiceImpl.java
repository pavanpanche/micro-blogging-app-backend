package com.blogging.subtxt.services.impl;

import com.blogging.subtxt.models.Follow;
import com.blogging.subtxt.models.User;
import com.blogging.subtxt.repository.FollowRepository;
import com.blogging.subtxt.repository.UserRepository;
import com.blogging.subtxt.services.FollowService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Override
    public void followUser(String followerEmail, String followingUsername) {
        if (followerEmail.equals(followingUsername)) {
            throw new IllegalArgumentException("You cannot follow yourself.");
        }

        User follower = userRepository.findByEmail(followerEmail)
                .orElseThrow(() -> new RuntimeException("Follower not found"));

        User following = userRepository.findByUsername(followingUsername)
                .orElseThrow(() -> new RuntimeException("User to follow not found"));

        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new RuntimeException("Already following this user");
        }

        Follow follow = new Follow(follower, following, LocalDateTime.now());
        followRepository.save(follow);
    }

    @Override
    public void unfollowUser(String followerEmail, String followingUsername) {
        User follower = userRepository.findByEmail(followerEmail)
                .orElseThrow(() -> new RuntimeException("Follower not found"));

        User following = userRepository.findByUsername(followingUsername)
                .orElseThrow(() -> new RuntimeException("User to unfollow not found"));

        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new RuntimeException("Not following this user"));

        followRepository.delete(follow);
    }

    @Override
    public boolean isFollowing(String followerEmail, String followingUsername) {
        User follower = userRepository.findByEmail(followerEmail)
                .orElseThrow(() -> new RuntimeException("Follower not found"));

        User following = userRepository.findByUsername(followingUsername)
                .orElseThrow(() -> new RuntimeException("User to check not found"));

        return followRepository.existsByFollowerAndFollowing(follower, following);
    }

    @Override
    public List<User> getFollowers(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return followRepository.findByFollowing(user)
                .stream()
                .map(Follow::getFollower)
                .toList();
    }

    @Override
    public List<User> getFollowing(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return followRepository.findByFollower(user)
                .stream()
                .map(Follow::getFollowing)
                .toList();
    }

    @Override
    public long getFollowerCount(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return followRepository.countByFollowing(user);
    }

    @Override
    public long getFollowingCount(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return followRepository.countByFollower(user);
    }

}
