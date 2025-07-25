package com.blogging.subtxt.repository;

import com.blogging.subtxt.models.Follow;
import com.blogging.subtxt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long>{

    boolean existsByFollowerAndFollowing(User follower, User following);
    void deleteByFollowerAndFollowing (User  follower, User Following);
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);

    long countByFollower (User follower);
    long countByFollowing (User following);

    List<Follow> findByFollower (User follower);
    List<Follow> findByFollowing (User following);
}
