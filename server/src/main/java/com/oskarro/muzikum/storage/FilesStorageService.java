package com.oskarro.muzikum.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesStorageService {

    void init();

    void save(MultipartFile file, String username);

    Resource load(String filename, String username);

    void deleteAll();

    Stream<Path> loadAll();
}
