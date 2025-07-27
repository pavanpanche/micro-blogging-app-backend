package com.blogging.subtxt.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TweetRequest {

    @NotBlank(message = "Tweet content must not be blank")
    @Size(max = 280, message = "Tweet must be under 280 characters")
    private String content;

}
