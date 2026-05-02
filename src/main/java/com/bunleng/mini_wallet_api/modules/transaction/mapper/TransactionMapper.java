package com.bunleng.mini_wallet_api.modules.transaction.mapper;

import com.bunleng.mini_wallet_api.modules.transaction.dto.TransactionRequest;
import com.bunleng.mini_wallet_api.modules.transaction.dto.TransactionResponse;
import com.bunleng.mini_wallet_api.modules.transaction.entity.Transaction;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isIncome", source = "isIncome", qualifiedByName = "mapIsIncome")
    Transaction toEntity(TransactionRequest request);


    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "formatDate")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "formatDate")
    TransactionResponse toResponse(Transaction transaction);


    @Named("formatDate")
    default String formatDate(LocalDateTime dateTime) {
        return dateTime == null
                ? null
                : DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dateTime);
    }

    @Named("mapIsIncome")
    default Boolean mapIsIncome(Boolean isIncome) {
        return Boolean.TRUE.equals(isIncome);
    }
}
