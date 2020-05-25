package com.oskarro.muzikum.monitor;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.playlist.Playlist;
import com.oskarro.muzikum.playlist.PlaylistRepository;
import com.oskarro.muzikum.playlist.PlaylistService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@SessionScope
public class SessionComponent {

    private AtomicInteger sessionViews;
    private final PlaylistService playlistService;
    private final PlaylistRepository playlistRepository;

    public SessionComponent(PlaylistService playlistService, PlaylistRepository playlistRepository) {
        this.playlistService = playlistService;
        this.playlistRepository = playlistRepository;
    }

    public Integer updateSessionViews(Integer playlistId) {
        Playlist playlist = playlistService.findPlaylistById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist", "id", playlistId));
        if (playlist.getViews() == null) {
            playlist.setViews(1);
        } else {
            playlist.setViews(playlist.getViews() + 1);
        }
        playlistRepository.save(playlist);
        return playlist.getViews();
    }
}
