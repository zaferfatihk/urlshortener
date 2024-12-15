package com.upwork.urlshortener.rest;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.upwork.urlshortener.exception.URLAlreadyExists;
import com.upwork.urlshortener.exception.URLExpiredException;
import com.upwork.urlshortener.model.Url;
import com.upwork.urlshortener.service.UrlService;
import com.upwork.urlshortener.service.UserService;


@RestController
public class UrlController {
    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);

    private final UrlService urlService;
    private final UserService userService;

    public UrlController(UrlService urlService, UserService userService) {
        this.userService = userService;
        this.urlService = urlService;
    }
    
    @PostMapping("/url")
    public ModelAndView shorten(@RequestParam("url") String url) throws Exception {

        if(urlService.urlExists(url)) {
            Url existingUrl = urlService.findByLongName(url);
            throw new URLAlreadyExists("URL already exists in database. Please use the short link generated: " + urlService.getBasePath() + existingUrl.getShortName());
        }

        String createdBy = userService.getLoggedInUser();
        LocalDateTime now = LocalDateTime.now();
        String shortName = urlService.getRandomString();
        LocalDateTime expiryDate = urlService.getExpiryDate(now);
        Url newUrl = Url.create(url, shortName, createdBy, now, expiryDate);
        Url savedUrl = urlService.save(newUrl);

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("shortName", savedUrl.getShortName());
        return modelAndView;
    }

    @GetMapping("/url/{shortUrl}")
    public ModelAndView redirect(@PathVariable("shortUrl") String shortUrl) {
        Url url = urlService.findByShortName(shortUrl);

        if(urlService.checkIfUrlExpired(url)) {
            throw new URLExpiredException("URL has expired");
        }

        ModelAndView modelAndView = new ModelAndView("redirect:" + url.getLongName());
        return modelAndView;
    }
}
