package com.upwork.urlshortener.service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public boolean isValidURL(String url) {
        // Regex to check valid URL
        String regex = "((http|https)://)(www.)?"
              + "[a-zA-Z0-9@:%._\\+~#?&//=]"
              + "{2,256}\\.[a-z]"
              + "{2,6}\\b([-a-zA-Z0-9@:%"
              + "._\\+~#?&//=]*)";
 
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
 
        // If the string is empty
        // return false
        if (url == null) {
            return false;
        }
 
        // Find match between given string
        // and regular expression
        // using Pattern.matcher()
        Matcher m = p.matcher(url);
 
        // Return if the string
        // matched the ReGex
        return m.matches();
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
        LocalDateTime expiryDate = timeNow.plusSeconds(getUrlExpireTime());
        return expiryDate;
    }

    public Url findByShortName(String shortUrl) {
        return urlRepository.findByShortName(shortUrl);
    }

    public boolean checkIfUrlExpired(Url url) {
        return url.getExpiryDate().isBefore(LocalDateTime.now());
    }

    public boolean urlExists(String url) {
        return urlRepository.findByLongName(url) != null;
    }

    public Url findByLongName(String longName) {
        return urlRepository.findByLongName(longName);
    }
}
