package com.oskarro.muzikum.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesStorageService {

    void init() throws IOException;

    Stream<Path> loadAll() throws IOException;

    void save(MultipartFile file, String username, String destination) throws IOException;

    void saveCover(MultipartFile file, String username, String trackUrl) throws IOException;

    void saveArticleImage(MultipartFile file, String username, Integer articleId) throws IOException;

    Resource loadImage(final String filename, final Object discriminator, final FileResourceType type) throws IOException;
}
