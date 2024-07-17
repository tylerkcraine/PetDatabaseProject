package com.revature.Exception;

public class ResetException extends RuntimeException {
    public ResetException(String message) {
        super(message);
    }

    public ResetException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
