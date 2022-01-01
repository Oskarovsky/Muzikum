package com.oskarro.muzikum.user.favorite;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.track.TrackService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/favorites")
public class FavoriteController {

    private final TrackService trackService;
    private final FavoriteTrackRepository favoriteTrackRepository;
    private final FavoriteService favoriteService;

    public FavoriteController(TrackService trackService, FavoriteTrackRepository favoriteTrackRepository,
                              FavoriteService favoriteService) {
        this.trackService = trackService;
        this.favoriteTrackRepository = favoriteTrackRepository;
        this.favoriteService = favoriteService;
    }

    @GetMapping(value = "/{username}/tracks")
    List<Track> getAllFavoriteTracks(@PathVariable String username) {
        List<FavoriteTrack> favoriteTracks = favoriteTrackRepository.findFavoriteTracksByUserUsername(username);
        List<Track> tracks = new ArrayList<>();
        favoriteTracks.forEach(x -> tracks.add(trackService.findById(x.getTrack().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Track", "id", x))));
        return tracks;
    }

    @GetMapping(value = "/{username}/tracks/ids")
    List<Integer> getAllFavoriteTracksIds(@PathVariable String username) {
        List<FavoriteTrack> favoriteTracks = favoriteTrackRepository.findFavoriteTracksByUserUsername(username);
        List<Track> tracks = new ArrayList<>();
        favoriteTracks.forEach(x -> tracks.add(trackService.findById(x.getTrack().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Track", "id", x))));
        List<Integer> tracksIds = new ArrayList<>();
        tracks.forEach(x -> tracksIds.add(x.getId()));
        return tracksIds;
    }

}
