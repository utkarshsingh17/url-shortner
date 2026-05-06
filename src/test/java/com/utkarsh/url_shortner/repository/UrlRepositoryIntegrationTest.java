package com.utkarsh.url_shortner.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.utkarsh.url_shortner.model.UrlEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlRepositoryIntegrationTest {
    @Autowired
    private UrlRepository urlRepository;

    @Test
    void shouldInsertAndGetFullurl() {
        UrlEntity urlEntity = new UrlEntity("http://example.com");
        urlRepository.save(urlEntity);

        assertNotNull(urlEntity.getId());

        UrlEntity urlEntityFromDb = urlRepository.findById(urlEntity.getId()).orElseThrow();
        assertEquals(urlEntity.getId(), urlEntityFromDb.getId());
    }
}
