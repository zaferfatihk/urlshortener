package com.upwork.urlshortener.exception;

public class URLAlreadyExists extends RuntimeException {
    public URLAlreadyExists(String message) {
        super(message);
    }
}