package com.example.kaddr.error;

public final class ApiCallException extends RuntimeException {
    public ApiCallException(final Throwable cause) {
        super(cause);
    }

    public ApiCallException(final String keyword, final Throwable cause) {
        super("Api Call Exception by keyword: " + keyword, cause);
    }
}