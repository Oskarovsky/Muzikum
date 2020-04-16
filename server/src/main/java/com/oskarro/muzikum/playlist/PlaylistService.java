package com.oskarro.muzikum.playlist;

import com.oskarro.muzikum.track.Track;

import java.util.List;
import java.util.Optional;

public interface PlaylistService {

    List<Playlist> getAllPlaylist();

    void addPlaylist(Playlist playlist);

    Optional<Playlist> findPlaylistById(Integer id);

    Optional<Playlist> findPlaylistByName(String name);

    Playlist updatePlaylist(Playlist playlist, Integer id);

    void deletePlaylistById(Integer id);

    List<Playlist> findAllPlaylistByUsername(String username);

}
