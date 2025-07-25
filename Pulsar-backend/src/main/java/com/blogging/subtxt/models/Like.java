package com.blogging.subtxt.models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "tweet_likes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "tweet_id"}))

public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tweet_id", referencedColumnName = "tweet_id")
    private Tweet tweet;
}
