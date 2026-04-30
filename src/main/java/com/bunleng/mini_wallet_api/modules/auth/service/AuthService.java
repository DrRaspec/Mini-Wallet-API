package com.bunleng.mini_wallet_api.modules.auth.service;

import com.bunleng.mini_wallet_api.core.exception.NotFoundException;
import com.bunleng.mini_wallet_api.core.utils.JwtUtil;
import com.bunleng.mini_wallet_api.modules.auth.dto.AuthResponse;
import com.bunleng.mini_wallet_api.modules.auth.dto.LoginRequest;
import com.bunleng.mini_wallet_api.modules.auth.dto.RegisterRequest;
import com.bunleng.mini_wallet_api.modules.auth.entity.User;
import com.bunleng.mini_wallet_api.modules.auth.mapper.AuthMapper;
import com.bunleng.mini_wallet_api.modules.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest req) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.username(),
                        req.password()
                )
        );

        User user = userRepository.findByUsername(req.username())
                .orElseThrow(() -> new NotFoundException("User not found"));

        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        return authMapper.toAuthResponse(user, accessToken, refreshToken);
    }

    public AuthResponse register(RegisterRequest req) {

        if (userRepository.findByUsername(req.username()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(req.username())
                .password(passwordEncoder.encode(req.password()))
                .active(true)
                .locked(false)
                .deleted(false)
                .build();

        userRepository.save(user);

        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        return authMapper.toAuthResponse(user, accessToken, refreshToken);
    }

    public AuthResponse refreshToken(String refreshToken) {
        if (!jwtUtil.validate(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String userId = jwtUtil.getUserId(refreshToken);

        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new NotFoundException("User not found"));

        String newAccessToken = jwtUtil.generateToken(user);

        return authMapper.toAuthResponse(user, newAccessToken, refreshToken);
    }
}
