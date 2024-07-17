package com.revature.Exception;

public class RecordAlreadyExistsException extends Exception{
    public RecordAlreadyExistsException(String message) {
        super(message);
    }

    public RecordAlreadyExistsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
