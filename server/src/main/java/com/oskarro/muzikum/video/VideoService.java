package com.oskarro.muzikum.video;

import java.util.List;
import java.util.Optional;

public interface VideoService {

    List<Video> getAllVideo();

    void addVideo(Video video);

    Optional findVideoById(Integer id);

    void deleteVideoById(Integer id);

}