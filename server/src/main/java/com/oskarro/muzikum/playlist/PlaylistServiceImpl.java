package com.oskarro.muzikum.playlist;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PlaylistServiceImpl implements PlaylistService {

    PlaylistRepository playlistRepository;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public List<Playlist> getAllPlaylist() {
        ;
    }

    @Override
    public Optional<Playlist> findPlaylistById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<Playlist> findPlaylistByName(String name) {
        return Optional.empty();
    }
}
