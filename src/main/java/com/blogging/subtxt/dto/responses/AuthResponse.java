package com.blogging.subtxt.dto.responses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AuthResponse {
   private String accessToken;
   private String refreshToken;
   private String username;
}
