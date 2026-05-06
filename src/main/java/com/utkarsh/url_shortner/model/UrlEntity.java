package com.utkarsh.url_shortner.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "url")
@Table(name = "url")
public class UrlEntity {
    private Long id;

    @Column(name = "full_url")
    private String fullUrl;

    public UrlEntity() {
    }

    public UrlEntity(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }
}
