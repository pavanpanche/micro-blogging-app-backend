package com.blogging.subtxt.controller;
import com.blogging.subtxt.dto.request.LoginRequest;
import com.blogging.subtxt.dto.request.RegisterRequest;
import com.blogging.subtxt.dto.responses.AuthResponse;
import com.blogging.subtxt.security.jwt.JwtUtil;
import com.blogging.subtxt.services.AuthService;
import com.blogging.subtxt.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    // ✅ Registration (only returns a success message)
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("Registration successful");
    }

    // login with email and password only
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.loginWithTokens(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(authResponse);
    }

    // old refresh token required for new access token
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestHeader("Authorization") String refreshTokenHeader) {
        String refreshToken = refreshTokenHeader.replace("Bearer ", "");
        AuthResponse response = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(response);
    }
}
