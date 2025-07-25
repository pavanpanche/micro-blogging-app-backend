package com.blogging.subtxt.dto.responses;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TweetResponse {
    private Long id;
    private String content;
    private String username;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
