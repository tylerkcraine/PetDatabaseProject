package com.revature.Exception;

public class RecordNotFoundException extends Exception {
    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
