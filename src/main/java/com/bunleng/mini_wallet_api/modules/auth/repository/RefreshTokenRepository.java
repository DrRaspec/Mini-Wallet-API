package com.bunleng.mini_wallet_api.modules.auth.repository;

import com.bunleng.mini_wallet_api.modules.auth.entity.RefreshToken;
import com.bunleng.mini_wallet_api.modules.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findAllByUserAndRevokedFalse(User user);
}
