package com.revature.Exception;

public class OptionalEmptyException extends Exception{
    public OptionalEmptyException(String message) {
        super(message);
    }

    public OptionalEmptyException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
