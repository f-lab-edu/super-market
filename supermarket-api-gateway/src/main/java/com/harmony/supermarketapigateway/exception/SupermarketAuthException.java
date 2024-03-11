package com.supermarket.gateway.auth.exception;

import lombok.Getter;

@Getter
public class SupermarketAuthException extends RuntimeException {
    private final String errorCode;

    public SupermarketAuthException(String errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }
}
