package com.oskarro.muzikum.storage;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public interface FilesStorageService {

    void init();

    Stream<Path> loadAll();

    Resource load(String filename, String username);

    Resource loadCover(String filename, Integer coverId);

    Resource loadArticleImage(String filename, Integer articleId);

    void save(MultipartFile file, String username, String destination);

    void saveCover(MultipartFile file, String username, String trackUrl);

    void saveArticleImage(MultipartFile file, String username, Integer articleId);
}
