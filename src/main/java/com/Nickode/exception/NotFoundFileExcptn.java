package com.Nickode.exception;

public class NotFoundFileExcptn extends RuntimeException{
    public NotFoundFileExcptn(String id) {
        super("Failed to find this file, id: " + id);
    }
}
