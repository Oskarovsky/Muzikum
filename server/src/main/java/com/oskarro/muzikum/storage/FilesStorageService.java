package com.oskarro.muzikum.storage;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public interface FilesStorageService {

    void init();

    void save(MultipartFile file, String username, String destination);

    Resource load(String filename, String username);

    Resource loadCover(String filename, Integer coverId);

    Stream<Path> loadAll();

    void saveCover(MultipartFile file, String username, String trackUrl);
}
