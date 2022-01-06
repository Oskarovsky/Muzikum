package com.oskarro.muzikum.storage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleImageRepository extends JpaRepository<ArticleImage, Integer> {

    Boolean existsByName(String fileName);

    ArticleImage findByName(String fileName);

    ArticleImage findByArticleId(Integer articleId);

    Optional<ArticleImage> findById(Integer articleImageId);

}
