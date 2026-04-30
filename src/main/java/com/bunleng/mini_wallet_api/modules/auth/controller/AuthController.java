package com.bunleng.mini_wallet_api.modules.auth.controller;

import com.bunleng.mini_wallet_api.modules.auth.dto.AuthResponse;
import com.bunleng.mini_wallet_api.modules.auth.dto.LoginRequest;
import com.bunleng.mini_wallet_api.modules.auth.dto.RefreshTokenRequest;
import com.bunleng.mini_wallet_api.modules.auth.dto.RegisterRequest;
import com.bunleng.mini_wallet_api.modules.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(
                request
        );
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(
                request
        );
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request.refreshToken());
    }
}
