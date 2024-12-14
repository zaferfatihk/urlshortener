package com.upwork.urlshortener.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UrlController {
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/secured")
    public String helloSecured() {
        return new String("Hello Secured World");
    }
    
} 
