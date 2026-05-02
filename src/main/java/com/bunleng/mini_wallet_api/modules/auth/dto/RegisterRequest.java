package com.bunleng.mini_wallet_api.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String confirmPassword
) {}