package com.blogging.subtxt.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeResponse {
    private Long tweetId;
    private Integer userId;
    private String username;
    private boolean likedByCurrentUser;
    private long totalLikes;
}
