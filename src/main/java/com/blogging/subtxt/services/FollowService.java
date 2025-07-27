package com.blogging.subtxt.services;

import com.blogging.subtxt.models.User;
import java.util.List;

public interface FollowService {
    void followUser(String followerEmail, String followingUsername);
    void unfollowUser(String followerEmail, String followingUsername);
    boolean isFollowing(String followerEmail, String followingUsername);

    List<User> getFollowers(String username);
    List<User> getFollowing(String username);

    long getFollowerCount(String username);
    long getFollowingCount(String username);
}
