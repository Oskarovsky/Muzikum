package com.oskarro.muzikum.playlist;

import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.track.TrackService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/playlist")
@CrossOrigin
public class PlaylistController {

    private final PlaylistService playlistService;
    private final TrackService trackService;
    private final PlaylistRepository playlistRepository;

    public PlaylistController(PlaylistService playlistService, TrackService trackService, PlaylistRepository playlistRepository) {
        this.playlistService = playlistService;
        this.trackService = trackService;
        this.playlistRepository = playlistRepository;
    }

    @GetMapping(value = "/findAll")
    List<Playlist> findAll() {
        return playlistService.getAllPlaylist();
    }

    @GetMapping(value = "/{id}")
    Optional<Playlist> getPlaylistById(@PathVariable Integer id) {
        return playlistService.findPlaylistById(id);
    }

    @GetMapping(value = "/{id}/tracks")
    List<Track> getAllTracksFromPlaylist(@PathVariable Integer id) {
        return trackService.findAllTracksFromPlaylist(id);
    }

    @PostMapping(value = "/add")
    public void addPlaylist(@RequestBody Playlist playlist) {
        playlistService.addPlaylist(playlist);
    }

/*    @PostMapping(value = "/{id}/addTrack")
    public void addTrackToPlaylist(@PathVariable Integer playlistId, @RequestBody Track track) {
        trackService.saveTrack(track);
        playlistService.addTrackToPlaylist(track, playlistId);
    }*/

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id) {
        this.playlistRepository.deleteById(id);
    }
}
