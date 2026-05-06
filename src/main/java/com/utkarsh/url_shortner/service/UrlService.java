package com.utkarsh.url_shortner.service;

import com.utkarsh.url_shortner.common.ShorteningUtil;
import com.utkarsh.url_shortner.dto.FullUrl;
import com.utkarsh.url_shortner.dto.ShortUrl;
import com.utkarsh.url_shortner.model.UrlEntity;
import com.utkarsh.url_shortner.repository.UrlRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UrlService {
    private static final Logger logger = LoggerFactory.getLogger(UrlService.class);
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    private UrlEntity get(Long id) {
        logger.info("Fetching url from database for id {}", id);
        return urlRepository.findById(id).orElseThrow();
    }

    /**
     * Uses Base62 decoding to convert short text to Base10 id and fetches the corresponding record.
     *
     * @param shortenString Base62 encoded string
     * @return FullUrl object
     */
    public FullUrl getFullUrl(String shortenString) {
        logger.debug("Converting Base62 string {} to Base10 id", shortenString);
        Long id = ShorteningUtil.strToId(shortenString);
        logger.info("Converted Base62 string {} to Base10 id {}", shortenString, id);
        return new FullUrl(this.get(id).getFullUrl());
    }

    private UrlEntity save(FullUrl fullUrl) {
        return urlRepository.save(new UrlEntity(fullUrl.getFullUrl()));
    }

    /**
     * Saves the full url (if not already present) and converts the id to Base62 short text.
     *
     * @param fullUrl FullUrl object to be converted to shortened url
     * @return ShortUrl object
     */
    public ShortUrl getShortUrl(FullUrl fullUrl) {
        logger.info("Checking whether url already exists: {}", fullUrl.getFullUrl());
        List<UrlEntity> savedUrls = checkFullUrlAlreadyExists(fullUrl);

        UrlEntity savedUrl;
        if (savedUrls.isEmpty()) {
            logger.info("Saving new url in database: {}", fullUrl.getFullUrl());
            savedUrl = this.save(fullUrl);
        } else {
            savedUrl = savedUrls.get(0);
            logger.info("Url already exists in database, skipping insert. id={}", savedUrl.getId());
        }

        logger.debug("Converting Base10 id {} to Base62", savedUrl.getId());
        String shortUrlText = ShorteningUtil.idToStr(savedUrl.getId());
        logger.info("Converted Base10 id {} to Base62 {}", savedUrl.getId(), shortUrlText);
        return new ShortUrl(shortUrlText);
    }

    /**
     * Check if url already exists in database to avoid duplicate rows for same full url.
     *
     * @param fullUrl incoming request dto
     * @return list of matching UrlEntity records
     */
    private List<UrlEntity> checkFullUrlAlreadyExists(FullUrl fullUrl) {
        return urlRepository.findUrlByFullUrl(fullUrl.getFullUrl());
    }
}
