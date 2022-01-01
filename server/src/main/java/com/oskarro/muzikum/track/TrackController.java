package com.oskarro.muzikum.track;

import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.track.model.TrackComment;
import com.oskarro.muzikum.track.model.TrackPageResponse;
import com.oskarro.muzikum.user.UserService;
import com.oskarro.muzikum.user.favorite.FavoriteService;
import com.oskarro.muzikum.user.favorite.FavoriteTrackRepository;
import lombok.AllArgsConstructor;
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

@RestController
@RequestMapping(value = "/api/tracks")
@CrossOrigin
public class TrackController {

    private final TrackService trackService;
    private final TrackRepository trackRepository;
    private final FavoriteService favoriteService;
    private final TrackDao trackDao;
    private final UserService userService;

    public TrackController(TrackService trackService,
                           TrackRepository trackRepository,
                           FavoriteService favoriteService,
                           TrackDao trackDao,
                           UserService userService) {
        this.trackService = trackService;
        this.trackRepository = trackRepository;
        this.favoriteService = favoriteService;
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
    public void addTrack(@RequestBody Track track, BindingResult bindingResult) throws ValidationException {
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
    public void addTrackToUserFavorites(@PathVariable Integer id, @PathVariable String username) {
        favoriteService.addTrackToFavorite(id, username);
    }

    @GetMapping(value = "/genre/{genre}/top")
    public Track getMostPopularTrackByGenre(@PathVariable String genre) {
        return trackService.getMostPopularTrackByGenre(genre);
    }

    @GetMapping(value = "/genre/{genre}/top/{numberOfTracks}")
    public List<Track> getListOfMostPopularTracksByGenre(@PathVariable String genre, @PathVariable Integer numberOfTracks) {
        return trackService.getListOfMostPopularTracksByGenre(genre, numberOfTracks);
    }

    @GetMapping(value = "/genre/{genre}/{numberOfTracks}")
    public List<Track> getLastAddedTracksByGenre(@PathVariable String genre, @PathVariable Integer numberOfTracks) {
        return trackService.getLastAddedTracksByGenre(genre, numberOfTracks);
    }

    @GetMapping(value = "/user/{username}/{numberOfTracks}")
    public List<Track> getLastAddedTracksByUsername(@PathVariable String username, @PathVariable Integer numberOfTracks) {
        return trackService.getLastAddedTracksByUsername(username, numberOfTracks);
    }

    @GetMapping(value = "/genre/{genre}/lastAddedByUser/{numberOfTracks}")
    public List<Track> getLastAddedTracksByGenreOnlyWithUser(@PathVariable String genre, @PathVariable Integer numberOfTracks) {
        return trackService.getLastAddedTracksByGenreOnlyWithUser(genre, numberOfTracks);
    }

    @GetMapping(value = "/getByUrl")
    public Optional<Track> getTrackByUrl(@RequestParam String url) {
        return trackService.getTrackByUrl(url);
    }

    @GetMapping(value = "/genre/{genre}/list")
    public ResponseEntity<Map<String, Object>> getAllTracks(
            @PathVariable String genre,
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

    @GetMapping(value = "/genre/{genre}/page/{idPage}")
    public List<Track> getTracksByGenreFromOnePage(@PathVariable String genre, @PathVariable int idPage) {
        return trackService.getAddedTracksByGenreFromPage(genre, idPage);
    }

    @GetMapping(value = "/genre/{genre}/pages/{idPage}")
    public TrackPageResponse getTrackPageByGenre(@PathVariable String genre, @PathVariable int idPage) {
        return trackService.getTrackPageByGenre(genre, idPage);
    }

    @GetMapping(value = "/user/{username}/pages/{idPage}")
    public TrackPageResponse getTrackPageByUserUsername(@PathVariable String username, @PathVariable int idPage) {
        return trackService.getTrackPageByUserUsername(username, idPage);
    }

    @GetMapping(value = "/id/{trackId}/comments")
    public List<TrackComment> getTrackCommentsByTrackId(@PathVariable Integer trackId) {
        return trackService.getAllTrackCommentsByTrackId(trackId);
    }

    @PostMapping(value = "/comment/add")
    public void addTrackComment(@RequestBody TrackComment trackComment, BindingResult bindingResult)
            throws ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("There are a problem with binding");
        }
        trackService.saveTrackComment(trackComment);
    }

    @DeleteMapping(value = "/comment/{commentId}/remove")
    public void deleteTrackCommentById(@PathVariable Integer commentId) {
        trackService.deleteTrackCommentById(commentId);
    }

    @GetMapping(value = "/user/{username}/count")
    public long getNumberOfTracksAddedByTheUser(@PathVariable String username) {
        return trackService.getNumberOfTracksAddedByTheUser(username);
    }
}
