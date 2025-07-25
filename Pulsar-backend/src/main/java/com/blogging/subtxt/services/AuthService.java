package com.blogging.subtxt.services;

import com.blogging.subtxt.dto.responses.AuthResponse;
import com.blogging.subtxt.security.MyUserDetailsService;
import com.blogging.subtxt.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final MyUserDetailsService userDetailsService;

    public AuthResponse loginWithTokens(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        UserDetails user = userDetailsService.loadUserByUsername(email);

        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(email);

        return new AuthResponse(accessToken, refreshToken, user.getUsername());
    }

    public AuthResponse refreshAccessToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken) || jwtUtil.isRefreshTokenExpired(refreshToken)) {
            throw new RuntimeException("Refresh token is invalid or expired. Please login again.");
        }

        String username = jwtUtil.extractUsername(refreshToken);
        String newAccessToken = jwtUtil.generateTokenFromUsername(username);

        // Optionally generate new refresh token (token rotation)
        // String newRefreshToken = jwtUtil.generateRefreshToken(username);

        return new AuthResponse(newAccessToken, refreshToken, username); // return the same or new refresh token
    }

}
