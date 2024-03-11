package com.supermarket.gateway.auth.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum AuthErrorCode {
    USER_NOT_FOUND("AUTH-ERROR-001", "계정 정보가 없습니다. 아이디, 비밀번호를 확인해주세요."),
    DUPLICATE_MEMBER_ID("AUTH-ERROR-002", "이미 존재하는 회원 ID입니다.");

    private final String code;
    private final String message;

    AuthErrorCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}