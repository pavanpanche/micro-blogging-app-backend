package com.blogging.subtxt.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "follows",
        uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "following_id"})
)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Who follows
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private User follower;

    // Who is being followed
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private User following;

    private LocalDateTime followedAt = LocalDateTime.now();

    public Follow(User follower, User following, LocalDateTime followedAt) {
        this.follower = follower;
        this.following = following;
        this.followedAt = followedAt;
    }
}
