package com.upwork.urlshortener.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class UrlServiceTest {
    @Autowired
    private UrlService urlService;

    @Test
    public void testIfTheUrlIsValid() throws Exception {
        String url = "http://example.com";
        boolean isValid = urlService.isValidURL(url);
        assertTrue(isValid);
    }

    @Test
    public void testWithEmptyURL() throws Exception {
        String url = null;
        boolean isValid = urlService.isValidURL(url);
        assertFalse(isValid);
    }
}
