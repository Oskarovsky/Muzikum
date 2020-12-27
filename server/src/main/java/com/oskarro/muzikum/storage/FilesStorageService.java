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

    void save(MultipartFile file, String username, String trackId, String destination);

    Resource load(String filename, String username);

    void deleteAll();

    Stream<Path> loadAll();

    Image findImageByFileName(String filename);

    Image findImageByUrl(String url);

    void saveCover(MultipartFile file, String username, String trackUrl);

    Cover getTrackCover(Integer trackId);
}
