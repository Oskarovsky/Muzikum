package com.oskarro.muzikum.playlist;

import com.oskarro.muzikum.monitor.SessionComponent;
import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.track.Track;
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

    @GetMapping(value = "/findAll")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    List<Playlist> findAll() {
        return playlistService.getAllPlaylist();
    }

    @GetMapping(value = "/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    Optional<Playlist> getPlaylistById(@PathVariable Integer id) {
        sessionComponent.updateSessionViews(id);
        return playlistService.findPlaylistById(id);
    }

    @GetMapping(value = "/{id}/tracks")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    List<Track> getAllTracksFromPlaylist(@PathVariable Integer id) {
        return trackService.findAllTracksFromPlaylist(id);
    }

    @GetMapping(value = "/{id}/updateViews")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    Integer getViewsNumber(@PathVariable Integer id) {
        return sessionComponent.updateSessionViews(id);
    }


    @PostMapping(value = "/add")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void addPlaylist(@RequestBody Playlist playlist) {
        playlistService.addPlaylist(playlist);
    }

    @DeleteMapping(value = "/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void delete(@PathVariable Integer id) {
        trackRepository.findTracksByPlaylistId(id).forEach(trackRepository::delete);
        this.playlistRepository.deleteById(id);
    }

    @GetMapping(value = "/all/{username}")
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
