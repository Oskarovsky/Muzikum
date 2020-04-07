package com.oskarro.muzikum.playlist;

import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.track.TrackRepository;
import com.oskarro.muzikum.track.TrackService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/playlist")
@CrossOrigin(origins = "http://localhost:4200")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final TrackService trackService;
    private final PlaylistRepository playlistRepository;
    private final TrackRepository trackRepository;

    public PlaylistController(PlaylistService playlistService, TrackService trackService, PlaylistRepository playlistRepository, TrackRepository trackRepository) {
        this.playlistService = playlistService;
        this.trackService = trackService;
        this.playlistRepository = playlistRepository;
        this.trackRepository = trackRepository;
    }

    @GetMapping(value = "/findAll")
    @CrossOrigin(origins = "http://localhost:4200")
    List<Playlist> findAll() {
        return playlistService.getAllPlaylist();
    }

    @GetMapping(value = "/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    Optional<Playlist> getPlaylistById(@PathVariable Integer id) {
        return playlistService.findPlaylistById(id);
    }

    @GetMapping(value = "/{id}/tracks")
    @CrossOrigin(origins = "http://localhost:4200")
    List<Track> getAllTracksFromPlaylist(@PathVariable Integer id) {
        return trackService.findAllTracksFromPlaylist(id);
    }

    @PostMapping(value = "/add")
    @CrossOrigin(origins = "http://localhost:4200")
    public void addPlaylist(@RequestBody Playlist playlist) {
        playlistService.addPlaylist(playlist);
    }

/*    @PostMapping(value = "/{id}/addTrack")
    public void addTrackToPlaylist(@PathVariable Integer playlistId, @RequestBody Track track) {
        trackService.saveTrack(track);
        playlistService.addTrackToPlaylist(track, playlistId);
    }*/

    @DeleteMapping(value = "/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public void delete(@PathVariable Integer id) {
        trackRepository.findTracksByPlaylistId(id).forEach(trackRepository::delete);
        this.playlistRepository.deleteById(id);
    }
}
