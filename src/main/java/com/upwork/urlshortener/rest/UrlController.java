package com.upwork.urlshortener.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/url")
public class UrlController {
    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);

    @PostMapping("/shorten")
    public String postMethodName(@RequestParam("url") String url) {
        logger.info("Shortening URL: " + url);        
        
        return "shortened";
    }
    
}
