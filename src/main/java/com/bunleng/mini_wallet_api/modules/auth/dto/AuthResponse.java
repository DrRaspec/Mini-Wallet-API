package com.bunleng.mini_wallet_api.modules.auth.dto;

import java.util.UUID;

public record AuthResponse(
        UUID userId,
        String username,
        String accessToken,
        String tokenType
) {}