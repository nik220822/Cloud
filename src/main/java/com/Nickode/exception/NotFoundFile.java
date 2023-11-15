package com.Nickode.exception;

public class NotFoundFile extends RuntimeException{
    public NotFoundFile(String id) {
        super("Failed to find this file, id: " + id);
    }
}
