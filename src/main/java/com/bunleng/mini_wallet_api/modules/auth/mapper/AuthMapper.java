package com.bunleng.mini_wallet_api.modules.auth.mapper;

import com.bunleng.mini_wallet_api.modules.auth.dto.AuthResponse;
import com.bunleng.mini_wallet_api.modules.auth.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "accessToken", source = "token")
    @Mapping(target = "tokenType", constant = "Bearer")
    AuthResponse toAuthResponse(User user, String token);
}