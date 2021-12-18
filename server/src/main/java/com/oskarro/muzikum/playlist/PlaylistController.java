package com.oskarro.muzikum.playlist;

import com.oskarro.muzikum.monitor.SessionComponent;
import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.track.TrackRepository;
import com.oskarro.muzikum.track.TrackService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/playlist")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final TrackService trackService;
    private final PlaylistRepository playlistRepository;
    private final TrackRepository trackRepository;
    private final SessionComponent sessionComponent;

    public PlaylistController(PlaylistService playlistService, TrackService trackService,
                              PlaylistRepository playlistRepository, TrackRepository trackRepository,
                              SessionComponent sessionComponent) {
        this.playlistService = playlistService;
        this.trackService = trackService;
        this.playlistRepository = playlistRepository;
        this.trackRepository = trackRepository;
        this.sessionComponent = sessionComponent;
    }

    @GetMapping(value = "/")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylist();
    }

    @GetMapping(value = "/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Optional<Playlist> getPlaylistById(@PathVariable Integer id) {
        return playlistService.findPlaylistById(id);
    }

    @GetMapping(value = "/{id}/tracks")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Track> getAllTracksFromPlaylist(@PathVariable Integer id) {
        return trackService.findAllTracksFromPlaylist(id);
    }

    @GetMapping(value = "/{id}/updateViews")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Integer getViewsNumber(@PathVariable Integer id) {
        return sessionComponent.updateSessionViews(id);
    }

    @PostMapping(value = "/")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Playlist addPlaylist(@RequestBody Playlist playlist) {
        return playlistService.save(playlist);
    }

    @DeleteMapping(value = "/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    void delete(@PathVariable Integer id) {
        trackRepository.deleteAll(trackRepository.findTracksByPlaylistId(id));
        this.playlistRepository.deleteById(id);
    }

    @GetMapping(value = "/user/{username}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Playlist> getAllPlaylistByUserName(@PathVariable String username) {
        return playlistService.findAllPlaylistByUsername(username);
    }

    @GetMapping(value = "/lastAdded/{quantity}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Playlist> getLastAddedPlaylist(@PathVariable Integer quantity) {
        return playlistService.getFirstPlaylists(quantity);
    }
}
