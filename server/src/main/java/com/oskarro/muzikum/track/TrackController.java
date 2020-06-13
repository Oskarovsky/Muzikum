package com.oskarro.muzikum.track;

import com.oskarro.muzikum.user.favorite.FavoriteService;
import com.oskarro.muzikum.user.favorite.FavoriteTrackRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/tracks")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TrackController {

    private final TrackService trackService;
    private final TrackRepository trackRepository;
    private final FavoriteTrackRepository favoriteTrackRepository;
    private final FavoriteService favoriteService;

    public TrackController(TrackService trackService, TrackRepository trackRepository,
                           FavoriteTrackRepository favoriteTrackRepository, FavoriteService favoriteService) {
        this.trackService = trackService;
        this.trackRepository = trackRepository;
        this.favoriteTrackRepository = favoriteTrackRepository;
        this.favoriteService = favoriteService;
    }

    @GetMapping(value = "/findAll")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    List<Track> findAll() {
        return trackService.findAll();
    }

    @GetMapping(value = "/id/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    Optional<Track> findById(@PathVariable Integer id) {
        return trackService.findById(id);
    }

    @PostMapping(value = "/add")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void addTrack(@RequestBody Track track, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("There are a problem with binding");
        }
        trackService.saveTrack(track);
    }

    @GetMapping(value = "/genre/{genre}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    List<Track> findByGenre(@PathVariable String genre) {
        return trackService.findTracksByGenre(genre);
    }

    @PostMapping(value = "/addToRanking")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    void addTrackToRanking (@RequestBody Track track, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Track has errors - it cannot by send");
        }
        Track trackAdded = Track.builder()
                .artist(track.getArtist())
                .title(track.getTitle())
                .version(track.getVersion())
                .build();
        trackService.saveTrack(trackAdded);
    }

    @DeleteMapping(value = "/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void delete(@PathVariable Integer id) {
        this.trackRepository.deleteById(id);
    }

    @GetMapping(value = "/random")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    Track getRandom() {
        return trackService.getRandomTrack();
    }

    @GetMapping(value = "/{id}/addToFavorites/{username}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void addTrackToFavorites(@PathVariable Integer id, @PathVariable String username) {
        favoriteService.addTrackToFavorite(id, username);
    }

    @GetMapping(value = "/genre/{genre}/top")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Track getMostPopularTrackByGenre(@PathVariable String genre) {
        return trackService.getMostPopularTrackByGenre(genre);
    }

    @GetMapping(value = "/genre/{genre}/top/{numberOfTracks}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Track> getListOfMostPopularTracksByGenre(@PathVariable String genre, @PathVariable Integer numberOfTracks) {
        return trackService.getListOfMostPopularTracksByGenre(genre, numberOfTracks);
    }

    @GetMapping(value = "/genre/{genre}/{numberOfTracks}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Track> getLastAddedTracksByGenre(@PathVariable String genre, @PathVariable Integer numberOfTracks) {
        return trackService.getLastAddedTracksByGenre(genre, numberOfTracks);
    }

    @GetMapping(value = "/user/{username}/{numberOfTracks}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Track> getLastAddedTracksByUsername(@PathVariable String username, @PathVariable Integer numberOfTracks) {
        return trackService.getLastAddedTracksByUsername(username, numberOfTracks);
    }

    @GetMapping(value = "/genre/{genre}/lastAddedByUser/{numberOfTracks}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Track> getLastAddedTracksByGenreOnlyWithUser(@PathVariable String genre, @PathVariable Integer numberOfTracks) {
        return trackService.getLastAddedTracksByGenreOnlyWithUser(genre, numberOfTracks);
    }

}
