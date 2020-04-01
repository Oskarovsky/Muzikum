package com.oskarro.muzikum.playlist;

import java.util.List;
import java.util.Optional;

public interface PlaylistService {

    List<Playlist> getAllPlaylist();

    Optional<Playlist> findPlaylistById(Integer id);

    Optional<Playlist> findPlaylistByName(String name);
}
