package com.utkarsh.url_shortner.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.utkarsh.url_shortner.dto.FullUrl;
import com.utkarsh.url_shortner.dto.ShortUrl;
import com.utkarsh.url_shortner.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

class UrlControllerIntegrationTest {
    @Test
    void shouldReturnShortenedUrl() {
        UrlService urlService = Mockito.mock(UrlService.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        UrlController controller = new UrlController(urlService);
        FullUrl fullUrl = new FullUrl("https://example.com/foo");
        ShortUrl shortUrl = new ShortUrl("abc");

        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/shorten"));
        when(urlService.getShortUrl(fullUrl)).thenReturn(shortUrl);

        ResponseEntity<Object> response = controller.saveUrl(fullUrl, request);
        ShortUrl body = (ShortUrl) response.getBody();
        assertEquals("http://localhost:8080/abc", body.getShortUrl());
    }

    @Test
    void shouldThrowNotFoundOnUnknownShortUrl() {
        UrlService urlService = Mockito.mock(UrlService.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        UrlController controller = new UrlController(urlService);

        when(urlService.getFullUrl("missing")).thenThrow(new NoSuchElementException());
        assertThrows(ResponseStatusException.class, () -> controller.redirectToFullUrl(response, "missing"));
    }
}
