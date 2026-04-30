package com.bunleng.mini_wallet_api.modules.auth.service;

import com.bunleng.mini_wallet_api.core.exception.NotFoundException;
import com.bunleng.mini_wallet_api.core.utils.JwtUtil;
import com.bunleng.mini_wallet_api.modules.auth.dto.AuthResponse;
import com.bunleng.mini_wallet_api.modules.auth.dto.LoginRequest;
import com.bunleng.mini_wallet_api.modules.auth.dto.RegisterRequest;
import com.bunleng.mini_wallet_api.modules.auth.entity.RefreshToken;
import com.bunleng.mini_wallet_api.modules.auth.entity.User;
import com.bunleng.mini_wallet_api.modules.auth.mapper.AuthMapper;
import com.bunleng.mini_wallet_api.modules.auth.repository.RefreshTokenRepository;
import com.bunleng.mini_wallet_api.modules.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

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

        saveRefreshToken(user, refreshToken);

        return authMapper.toAuthResponse(user, accessToken, refreshToken);
    }

    public AuthResponse refresh(String refreshToken) {

        RefreshToken stored = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (stored.isRevoked() || stored.isExpired()) {
            revokeAllUserTokens(stored.getUser());
            throw new RuntimeException("Token reuse detected");
        }

        if (stored.getExpiryDate().isBefore(LocalDateTime.now())) {
            stored.setExpired(true);
            refreshTokenRepository.save(stored);
            throw new RuntimeException("Refresh token expired");
        }

        User user = stored.getUser();

        stored.setRevoked(true);
        stored.setExpired(true);
        refreshTokenRepository.save(stored);

        String newAccessToken = jwtUtil.generateToken(user);
        String newRefreshToken = jwtUtil.generateRefreshToken(user);

        saveRefreshToken(user, newRefreshToken);

        return authMapper.toAuthResponse(user, newAccessToken, newRefreshToken);
    }

    private RefreshToken saveRefreshToken(User user, String token) {

        RefreshToken rt = RefreshToken.builder()
                .user(user)
                .token(token)
                .revoked(false)
                .expired(false)
                .issuedAt(LocalDateTime.now())
                .expiryDate(LocalDateTime.now().plusDays(7))
                .build();

        return refreshTokenRepository.save(rt);
    }

    private void revokeAllUserTokens(User user) {

        List<RefreshToken> tokens =
                refreshTokenRepository.findAllByUserAndRevokedFalse(user);

        tokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });

        refreshTokenRepository.saveAll(tokens);
    }

    public void logout(String refreshToken) {

        RefreshToken stored = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow();

        stored.setRevoked(true);
        stored.setExpired(true);

        refreshTokenRepository.save(stored);
    }
}
