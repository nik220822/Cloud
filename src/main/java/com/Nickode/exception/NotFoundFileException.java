package com.Nickode.exception;

public class NotFoundFileException extends RuntimeException {
    public NotFoundFileException(String id) {
        super("Failed to find this file, id: " + id);
    }
}
