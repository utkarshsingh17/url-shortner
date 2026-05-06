package com.utkarsh.url_shortner.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.MalformedURLException;
import org.junit.jupiter.api.Test;

class UrlUtilTest {
    @Test
    void shouldThrowExceptionWhenMalformedUrlSuppliedWithoutProtocol() {
        assertThrows(MalformedURLException.class, () -> UrlUtil.getBaseUrl("malformed url dummy text"));
    }

    @Test
    void shouldThrowExceptionWhenMalformedUrlSuppliedWithIllegalChars() {
        assertThrows(MalformedURLException.class, () -> UrlUtil.getBaseUrl("malformed://example.com/foo"));
    }

    @Test
    void shouldReturnBaseUrlWhenValidUrlSuppliedWithoutPort() throws MalformedURLException {
        assertEquals("http://example.com/", UrlUtil.getBaseUrl("http://example.com/foo"));
    }

    @Test
    void shouldReturnBaseUrlWhenValidUrlSuppliedWithPort() throws MalformedURLException {
        assertEquals("http://example.com:8080/", UrlUtil.getBaseUrl("http://example.com:8080/foo"));
    }
}
