package com.upwork.urlshortener.exception;

public class URLExpiredException extends RuntimeException {
    public URLExpiredException(String message) {
        super(message);
    }
}
