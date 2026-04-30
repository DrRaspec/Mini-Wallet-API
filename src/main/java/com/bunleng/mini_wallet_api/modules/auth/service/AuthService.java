package com.bunleng.mini_wallet_api.modules.auth.service;

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
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getDeleted() || !user.getActive()) {
            throw new RuntimeException("User is inactive");
        }

        user.setLastLogin(java.time.LocalDateTime.now());
        userRepository.save(user);

        String token = jwtUtil.generateToken(user);

        return authMapper.toAuthResponse(user, token);
    }

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.username())) {
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

        String token = jwtUtil.generateToken(user);

        return authMapper.toAuthResponse(user, token);
    }
}
