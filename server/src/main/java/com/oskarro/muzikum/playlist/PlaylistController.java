package com.oskarro.muzikum.playlist;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.monitor.SessionComponent;
import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.track.TrackRepository;
import com.oskarro.muzikum.track.TrackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/playlist")
@CrossOrigin
public class PlaylistController {

    private final PlaylistService playlistService;
    private final TrackService trackService;
    private final PlaylistRepository playlistRepository;
    private final TrackRepository trackRepository;
    private final SessionComponent sessionComponent;

    public PlaylistController(final PlaylistService playlistService,
                              final TrackService trackService,
                              final PlaylistRepository playlistRepository,
                              final TrackRepository trackRepository,
                              final SessionComponent sessionComponent) {
        this.playlistService = playlistService;
        this.trackService = trackService;
        this.playlistRepository = playlistRepository;
        this.trackRepository = trackRepository;
        this.sessionComponent = sessionComponent;
    }

    @GetMapping
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylist();
    }

    @GetMapping(value = "/{id}")
    public Optional<Playlist> getPlaylistById(@PathVariable Integer id) {
        return playlistService.findPlaylistById(id);
    }

    @GetMapping(value = "/{id}/tracks")
    public List<Track> getAllTracksFromPlaylist(@PathVariable Integer id) {
        return trackService.findAllTracksFromPlaylist(id);
    }

    @GetMapping(value = "/{id}/views")
    public Integer getViewsNumber(@PathVariable Integer id) {
        return sessionComponent.updateSessionViews(id);
    }

    @PostMapping
    public Playlist addPlaylist(@RequestBody Playlist playlist) {
        return playlistService.save(playlist);
    }

    @DeleteMapping(value = "/{id}")
    void delete(@PathVariable Integer id) {
        trackRepository.deleteAll(trackRepository.findTracksByPlaylistId(id));
        this.playlistRepository.deleteById(id);
    }

    @GetMapping(value = "/user/{username}")
    public List<Playlist> getAllPlaylistByUserName(@PathVariable String username) {
        return playlistService.findAllPlaylistByUsername(username);
    }

    @GetMapping(value = "/lastAdded/{quantity}")
    public List<Playlist> getLastAddedPlaylist(@PathVariable Integer quantity) {
        return playlistService.getFirstPlaylists(quantity);
    }

    @PostMapping(value = "/{playlistId}/track")
    public Playlist addTrackToPlaylist(@RequestBody final Track track,
                                       @PathVariable final String playlistId) {
        Playlist playlist = playlistRepository.findById(Integer.valueOf(playlistId))
                .orElseThrow(() -> new ResourceNotFoundException("Playlist", "id", playlistId));
        track.setPlaylist(playlist);
        track.setPoints(0);
        Track trackResponse = trackRepository.save(track);
        log.info("Track {} has been saved to playlist with id {}", trackResponse, playlistId);
        return playlist;
    }
}
