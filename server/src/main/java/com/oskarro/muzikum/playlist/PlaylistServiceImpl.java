package com.oskarro.muzikum.playlist;

import com.oskarro.muzikum.track.Track;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlaylistServiceImpl implements PlaylistService {

    PlaylistRepository playlistRepository;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public List<Playlist> getAllPlaylist() {
        return playlistRepository.findAll();
    }

    @Override
    public Optional<Playlist> findPlaylistById(Integer id) {
        return playlistRepository.findById(id);
    }

    @Override
    public Optional<Playlist> findPlaylistByName(String name) {
        return playlistRepository.findByName(name);
    }

    @Override
    public void addPlaylist(Playlist playlist) {
        playlistRepository.save(playlist);
    }

    @Override
    public Playlist updatePlaylist(Playlist playlist, Integer id) {
        return null;
    }

    @Override
    public void deletePlaylistById(Integer id) {
        playlistRepository.deleteById(id);
    }

    @Override
    public List<Playlist> findAllPlaylistByUsername(String username) {
        return playlistRepository.findAllByUserUsername(username);
    }

    @Override
    public List<Playlist> getFirstPlaylists(Integer numberOfPlaylists) {
        List<Playlist> allPlaylists = playlistRepository.findAll();
        return allPlaylists
                .stream()
                .limit(numberOfPlaylists)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
