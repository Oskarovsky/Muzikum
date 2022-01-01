package com.oskarro.muzikum.track;

import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.track.model.TrackComment;
import com.oskarro.muzikum.track.model.TrackPageResponse;
import com.oskarro.muzikum.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.xml.bind.ValidationException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/tracks")
@CrossOrigin
public class TrackController {

    private final TrackService trackService;
    private final TrackRepository trackRepository;
    private final TrackFavoriteService trackFavoriteService;
    private final TrackDao trackDao;
    private final UserService userService;

    public TrackController(TrackService trackService,
                           TrackRepository trackRepository,
                           TrackFavoriteService trackFavoriteService,
                           TrackDao trackDao,
                           UserService userService) {
        this.trackService = trackService;
        this.trackRepository = trackRepository;
        this.trackFavoriteService = trackFavoriteService;
        this.trackDao = trackDao;
        this.userService = userService;
    }

    @GetMapping
    @Transactional
    List<Track> findAll() {
        return trackDao.findAll();
    }

    @GetMapping(value = "/{trackId}")
    Optional<Track> findById(@PathVariable Integer trackId) {
        return trackService.findById(trackId);
    }

    @PostMapping
    public void addTrack(@RequestBody Track track,
                         BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("There are a problem with binding");
        }
        if (track.getUser() != null) {
            userService.updateUserStatistics(track.getUser());
        }
        track.setPoints(0);
        trackService.saveTrack(track);
    }

    @GetMapping(value = "/genre/{genre}")
    List<Track> findByGenre(@PathVariable String genre) {
        return trackService.findTracksByGenre(genre);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id) {
        this.trackRepository.deleteById(id);
    }

    @GetMapping(value = "/random")
    Track getRandom() {
        return trackService.getRandomTrack();
    }

    @GetMapping(value = "/{id}/user/{username}/favorites")
    public void addTrackToUserFavorites(@PathVariable Integer id,
                                        @PathVariable String username) {
        trackFavoriteService.addTrackToFavorite(id, username);
    }

    @GetMapping(value = "/genre/{genre}/top")
    public Track getMostPopularTrackByGenre(@PathVariable String genre) {
        return trackService.getMostPopularTrackByGenre(genre);
    }

    @GetMapping(value = "/genre/{genre}/top/{numberOfTracks}")
    public List<Track> getListOfMostPopularTracksByGenre(@PathVariable String genre,
                                                         @PathVariable Integer numberOfTracks) {
        return trackService.getListOfMostPopularTracksByGenre(genre, numberOfTracks);
    }

    @GetMapping(value = "/genre/{genre}/{numberOfTracks}")
    public List<Track> getLastAddedTracksByGenre(@PathVariable String genre,
                                                 @PathVariable Integer numberOfTracks) {
        return trackService.getLastAddedTracksByGenre(genre, numberOfTracks);
    }

    @GetMapping(value = "/user/{username}/{numberOfTracks}")
    public List<Track> getLastAddedTracksByUsername(@PathVariable String username,
                                                    @PathVariable Integer numberOfTracks) {
        return trackService.getLastAddedTracksByUsername(username, numberOfTracks);
    }

    @GetMapping(value = "/genre/{genre}/list")
    public ResponseEntity<Map<String, Object>> getAllTracks(@PathVariable String genre,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            List<Track> tracks;
            Pageable paging = PageRequest.of(page, size);
            Page<Track> pageTracks;
            pageTracks = trackRepository.findByGenreAndUrlSourceIsNotNullOrderByCreatedAtDesc(genre.toUpperCase(), paging);
            tracks = pageTracks.getContent();
            if (tracks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map<String, Object> response = new HashMap<>();
            response.put("tracks", tracks);
            response.put("currentPage", pageTracks.getNumber());
            response.put("totalItems", pageTracks.getTotalElements());
            response.put("totalPages", pageTracks.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/genre/{genre}/page/{pageNumber}")
    public List<Track> getTracksByGenreFromOnePage(@PathVariable String genre,
                                                   @PathVariable int pageNumber) {
        return trackService.getAddedTracksByGenreFromPage(genre, pageNumber);
    }

    @GetMapping(value = "/genre/{genre}/pages/{pageNumber}")
    public TrackPageResponse getTrackPageByGenre(@PathVariable String genre,
                                                 @PathVariable int pageNumber) {
        return trackService.getTrackPageByGenre(genre, pageNumber);
    }

    @GetMapping(value = "/user/{username}/pages/{pageNumber}")
    public TrackPageResponse getTrackPageByUserUsername(@PathVariable String username,
                                                        @PathVariable int pageNumber) {
        return trackService.getTrackPageByUserUsername(username, pageNumber);
    }

    @GetMapping(value = "/{trackId}/comments")
    public List<TrackComment> getTrackCommentsByTrackId(@PathVariable Integer trackId) {
        return trackService.getAllTrackCommentsByTrackId(trackId);
    }

    @PostMapping(value = "/comment/add")
    public void addTrackComment(@RequestBody TrackComment trackComment,
                                BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("There are a problem with binding");
        }
        trackService.saveTrackComment(trackComment);
    }

    @DeleteMapping(value = "/comment/{commentId}")
    public void deleteTrackCommentById(@PathVariable Integer commentId) {
        trackService.deleteTrackCommentById(commentId);
    }

    @GetMapping(value = "/user/{username}/count")
    public long getNumberOfTracksAddedByTheUser(@PathVariable String username) {
        return trackService.getNumberOfTracksAddedByTheUser(username);
    }

    @GetMapping(value = "/favorites/user/{username}")
    List<Track> getAllFavoriteTracks(@PathVariable final String username) {
        return trackFavoriteService.getFavoriteTracksByUsername(username)
                .stream()
                .map(t -> trackService.findById(t.getTrack().getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/favorites/user/{username}/ids")
    List<Integer> getAllFavoriteTracksIds(@PathVariable final String username) {
        return trackFavoriteService.getFavoriteTracksByUsername(username)
                .stream()
                .map(t -> trackService.findById(t.getTrack().getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Track::getId)
                .collect(Collectors.toList());
    }
}
