package com.upwork.urlshortener.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.upwork.urlshortener.model.Url;

@Repository
public interface UrlRepository extends ListCrudRepository<Url, Long> {
    
}
