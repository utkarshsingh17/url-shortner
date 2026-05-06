package com.utkarsh.url_shortner;

import com.utkarsh.url_shortner.service.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlShortnerApplicationTests {
	@Autowired
	private UrlService urlService;

	@Test
	void contextLoads() {
	}

}
