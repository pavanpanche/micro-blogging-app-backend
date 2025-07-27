package com.blogging.subtxt.repository;

import com.blogging.subtxt.models.Tweet;
import com.blogging.subtxt.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    //  Already Present but (non-paginated)
    List<Tweet> findByUser(User user);

    //  Paginated tweets by specific user
    Page<Tweet> findByUser(User user, Pageable pageable);

    List<Tweet> findByContentContainingIgnoreCase(String content);

    //  User + following feed
    @Query("""
        SELECT t FROM Tweet t
        WHERE t.user.id = :userId
           OR t.user.id IN (
               SELECT f.following.id FROM Follow f WHERE f.follower.id = :userId
           )
        ORDER BY t.createdAt DESC
    """)
    Page<Tweet> findHomeFeedTweets(@Param("userId") Integer userId, Pageable pageable);

    // Recent tweets (for home recent section)
    @Query("SELECT t FROM Tweet t ORDER BY t.createdAt DESC")
    Page<Tweet> findAllRecentTweets(Pageable pageable);

    List<Tweet> findByHashtags_TagIgnoreCase(String tag);

}
