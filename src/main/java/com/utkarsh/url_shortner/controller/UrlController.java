package com.utkarsh.url_shortner.controller;

import com.utkarsh.url_shortner.common.UrlUtil;
import com.utkarsh.url_shortner.dto.FullUrl;
import com.utkarsh.url_shortner.dto.ShortUrl;
import com.utkarsh.url_shortner.error.InvalidUrlError;
import com.utkarsh.url_shortner.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.NoSuchElementException;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<Object> saveUrl(@RequestBody FullUrl fullUrl, HttpServletRequest request) {
        UrlValidator validator = new UrlValidator(new String[]{"http", "https"});
        if (!validator.isValid(fullUrl.getFullUrl())) {
            InvalidUrlError error = new InvalidUrlError("url", fullUrl.getFullUrl(), "Invalid URL");
            return ResponseEntity.badRequest().body(error);
        }

        String baseUrl;
        try {
            baseUrl = UrlUtil.getBaseUrl(request.getRequestURL().toString());
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request url is invalid", e);
        }

        ShortUrl shortUrl = urlService.getShortUrl(fullUrl);
        shortUrl.setShortUrl(baseUrl + shortUrl.getShortUrl());
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortenString}")
    public void redirectToFullUrl(HttpServletResponse response, @PathVariable String shortenString) {
        try {
            FullUrl fullUrl = urlService.getFullUrl(shortenString);
            response.sendRedirect(fullUrl.getFullUrl());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Url not found", e);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not redirect to the full url", e);
        }
    }
}
