package com.upwork.urlshortener.service;

import java.time.LocalDateTime;
import java.util.Base64;
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

    @Value("${url.expire.time}")
    private int urlExpireTime;

    public int getUrlExpireTime() {
        return urlExpireTime;
    }

    public String getRandomString() {
        int length = 6;
        String randomString = new Random().ints(48, 122).filter(i -> (i < 58 || i > 64) && (i < 91 || i > 96)).limit(length).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        return randomString;
    }

    public String decode(String encodedUrl) {
        return new String(Base64.getUrlDecoder().decode(encodedUrl));
    }

    public Url save(Url urlEntity) {
        return urlRepository.save(urlEntity);
    }

    public LocalDateTime getExpiryDate(LocalDateTime timeNow) {
        LocalDateTime expiryDate = timeNow.plusMinutes(getUrlExpireTime());
        return expiryDate;
    }
}
