package com.oskarro.muzikum.playlist;

import java.util.List;
import java.util.Optional;

public interface PlaylistService {

    List<Playlist> getAllPlaylist();

    Playlist save(Playlist playlist);

    Optional<Playlist> findPlaylistById(Integer id);

    Optional<Playlist> findPlaylistByName(String name);

    Playlist updatePlaylist(Playlist playlist, Integer id);

    void deletePlaylistById(Integer id);

    List<Playlist> findAllPlaylistByUsername(String username);

    List<Playlist> getFirstPlaylists(Integer numberOfPlaylists);
}
