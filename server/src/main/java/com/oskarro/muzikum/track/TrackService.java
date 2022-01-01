package com.oskarro.muzikum.track;

import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.track.model.TrackComment;
import com.oskarro.muzikum.track.model.TrackPageResponse;

import java.util.List;
import java.util.Optional;

public interface TrackService {

    List<Track> findAll();

    Optional<Track> findById(Integer id);

    Track saveTrack(Track track);

    List<Track> findTracksByGenre(String genre);

    List<Track> findAllTracksFromPlaylist(Integer id);

    List<Track> findAllTracksFromVideo(Integer id);

    Track getRandomTrack();

    Track getMostPopularTrackByGenre(String genre);

    List<Track> getListOfMostPopularTracksByGenre(String genre, Integer numberOfTracks);

    List<Track> getLastAddedTracksByGenre(String genre, Integer numberOfTracks);

    List<Track> getLastAddedTracksByUsername(String username, Integer numberOfTracks);

    List<Track> getAddedTracksByGenreFromPage(String genre, int page);

    TrackPageResponse getTrackPageByGenre(String genre, int page);

    TrackPageResponse getTrackPageByUserUsername(String username, int page);

    List<TrackComment> getAllTrackCommentsByTrackId(Integer id);

    TrackComment saveTrackComment(TrackComment trackComment);

    void deleteTrackCommentById(Integer commentId);

    long getNumberOfTracksAddedByTheUser(String username);
}
