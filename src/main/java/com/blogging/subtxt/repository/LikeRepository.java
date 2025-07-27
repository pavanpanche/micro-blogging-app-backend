package com.blogging.subtxt.repository;

import com.blogging.subtxt.models.Like;
import com.blogging.subtxt.models.Tweet;
import com.blogging.subtxt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // Used for toggle like/unlike functionality
    Optional<Like> findByTweetAndUser(Tweet tweet, User user);

    // Used to check like status (e.g., for UI toggle button)
    boolean existsByTweetAndUser(Tweet tweet, User user);

    // Used to show total like count on a tweet
    long countByTweet(Tweet tweet);

    // Optional: get all likes for a specific tweet
    // List<Like> findByTweet(Tweet tweet);

    // Optional: get all likes made by a specific user
    // List<Like> findByUser(User user);
}
