package com.utkarsh.url_shortner.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ShorteningUtilTest {
    @Test
    void shouldConvertMaxLongToShortString() {
        String maxIdShortString = ShorteningUtil.idToStr(Long.MAX_VALUE);
        assertNotNull(maxIdShortString);
        assertFalse(maxIdShortString.isEmpty());
    }

    @Test
    void shouldConvertStringToLong() {
        Long id = ShorteningUtil.strToId("sclqgMAPqi2Z");
        assertNotNull(id);
    }
}
