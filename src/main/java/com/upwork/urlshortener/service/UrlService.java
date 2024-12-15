package com.upwork.urlshortener.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.upwork.urlshortener.model.Url;
import com.upwork.urlshortener.repository.UrlRepository;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Value("${base.path}")
    private String basePath;

    public String getBasePath() {
        return basePath;
    }

    @Value("${url.expire.time}")
    private int urlExpireTime;

    public int getUrlExpireTime() {
        return urlExpireTime;
    }

    public String getRandomString() {
        int length = 6;
        String randomString = new Random()
        .ints(48, 122) //These integers correspond to ASCII values.
        .filter(i -> (i < 58 || i > 64) && (i < 91 || i > 96)) //ensures that only alphanumeric characters are included
        .limit(length)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
        return randomString;
    }

    public Url save(Url urlEntity) {
        return urlRepository.save(urlEntity);
    }

    public LocalDateTime getExpiryDate(LocalDateTime timeNow) {
        LocalDateTime expiryDate = timeNow.plusMinutes(getUrlExpireTime());
        return expiryDate;
    }

    public Url findByShortName(String shortUrl) {
        return urlRepository.findByShortName(shortUrl);
    }

    public boolean checkIfUrlExpired(Url url) {
        return url.getExpiryDate().isBefore(LocalDateTime.now());
    }
}
