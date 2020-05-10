package com.oskarro.muzikum.video;

import com.oskarro.muzikum.playlist.Playlist;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {

    VideoRepository videoRepository;

    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public List<Video> getAllVideo() {
        return videoRepository.findAll();
    }

    @Override
    public List<Video> findVideosByCategory(String category) {
        return videoRepository.findVideosByCategory(category);
    }

    @Override
    public void addVideo(Video video) {
        videoRepository.save(video);
    }

    @Override
    public Optional<Video> findVideoById(Integer id) {
        return videoRepository.findById(id);
    }

    @Override
    public void deleteVideoById(Integer id) {
        videoRepository.deleteById(id);
    }

    @Override
    public Playlist getPlaylistFromVideoById(Integer id) {
        Video video = videoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: Video cannot be found"));
        return video.getPlaylist();
    }
}
