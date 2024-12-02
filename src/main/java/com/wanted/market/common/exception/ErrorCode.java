package com.wanted.market.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 4XX Client Error
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다"),
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "리소스가 이미 존재합니다"),
    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "해당 리소스에 대한 권한이 없습니다"),

    // User 도메인
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),

    // Product 도메인
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다"),
    INVALID_PRODUCT_STATUS(HttpStatus.BAD_REQUEST, "상품 상태가 올바르지 않습니다"),
    PRODUCT_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "상품이 현재 구매 가능한 상태가 아닙니다"),
    INSUFFICIENT_STOCK(HttpStatus.BAD_REQUEST, "상품의 재고가 부족합니다"),

    // Transaction 도메인
    INVALID_TRANSACTION_STATUS(HttpStatus.BAD_REQUEST, "거래 상태가 올바르지 않습니다"),

    // 5XX Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다");

    private final HttpStatus status;
    private final String message;
}