package com.utkarsh.url_shortner.common;

import java.net.MalformedURLException;
import java.net.URL;

public final class UrlUtil {
    private UrlUtil() {
    }

    public static String getBaseUrl(String url) throws MalformedURLException {
        URL reqUrl = new URL(url);
        String protocol = reqUrl.getProtocol();
        String host = reqUrl.getHost();
        int port = reqUrl.getPort();

        if (port == -1) {
            return "%s://%s/".formatted(protocol, host);
        }
        return "%s://%s:%d/".formatted(protocol, host, port);
    }
}
