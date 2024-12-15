package com.upwork.urlshortener.rest;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.upwork.urlshortener.model.Url;
import com.upwork.urlshortener.service.UrlService;


@RestController
public class UrlController {
    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }
    
    @PostMapping("/url")
    public ModelAndView shorten(@RequestParam("name") String url, @RequestParam("name") String name) {

        LocalDateTime now = LocalDateTime.now();

        String shortName = urlService.getRandomString();
        Url urlEntity = new Url();
        urlEntity.setLongName(url);
        urlEntity.setShortName(shortName);
        urlEntity.setCreateDate(now);
        urlEntity.setExpiryDate(urlService.getExpiryDate(now));
        urlEntity.setCreateBy(name);
        urlService.save(urlEntity);
        
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("shortName", shortName);
        return modelAndView;
    }
    
}
