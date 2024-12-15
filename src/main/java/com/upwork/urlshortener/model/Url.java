package com.upwork.urlshortener.model;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "url")
public class Url{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    private String longName;
    private String shortName;
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime expiryDate;

    public static Url create(String longName, String shortName, String createdBy, LocalDateTime createDate, LocalDateTime expiryDate) {
        Url url = new Url();
        url.setLongName(longName);
        url.setShortName(shortName);
        url.setCreatedBy(createdBy);
        url.setCreatedDate(createDate);
        url.setExpiryDate(expiryDate);
        return url;
    }
}