package com.utkarsh.url_shortner.repository;

import com.utkarsh.url_shortner.model.UrlEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
    @Query("SELECT u FROM url u WHERE u.fullUrl = ?1")
    List<UrlEntity> findUrlByFullUrl(String fullUrl);
}
