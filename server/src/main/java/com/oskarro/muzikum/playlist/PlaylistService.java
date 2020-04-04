package com.oskarro.muzikum.playlist;

import com.oskarro.muzikum.track.Track;

import java.util.List;
import java.util.Optional;

public interface PlaylistService {

    List<Playlist> getAllPlaylist();

    void addPlaylist(Playlist playlist);

    Optional<Playlist> findPlaylistById(Integer id);

    Optional<Playlist> findPlaylistByName(String name);

    void addTrackToPlaylist(Track track, Integer id);

    void removeTrackFromPlaylist(Track track, Integer id);
}
