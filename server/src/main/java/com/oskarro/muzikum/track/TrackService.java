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

    List<Track> findByProviderId(Integer id);

    List<Track> findTracksByGenre(String genre);

    List<Track> findByProviderIdAndGenre(Integer id, String genre);

    List<Track> findRandomTracksByProviderIdAndGenre(Integer id, String genre, int numberOfTracks);

    List<Track> findRandomTracksByProviderForAllGenres(Integer providerId, int numberOfTracks);

    List<Track> findTracksByProviderName(String name);

    List<Track> findAllTracksFromPlaylist(Integer id);

    List<Track> findAllTracksFromVideo(Integer id);

    Track getRandomTrack();

    void addTrackToFavorite(Integer trackId, String username);

    Track getMostPopularTrackByGenre(String genre);

    List<Track> getListOfMostPopularTracksByGenre(String genre, Integer numberOfTracks);

    List<Track> getLastAddedTracksByGenre(String genre, Integer numberOfTracks);

    List<Track> getLastAddedTracksByUsername(String username, Integer numberOfTracks);

    List<Track> getLastAddedTracksByGenreOnlyWithUser(String genre, Integer numberOfTracks);

    List<Track> getAddedTracksByGenreFromPage(String genre, int page);

    TrackPageResponse getTrackPageByGenre(String genre, int page);

    TrackPageResponse getTrackPageByUserUsername(String username, int page);

    List<TrackComment> getAllTrackCommentsByTrackId(Integer id);

    Optional<Track> getTrackByUrl(String url);

    TrackComment saveTrackComment(TrackComment trackComment);

    void deleteTrackCommentById(Integer commentId);

    long getNumberOfTracksAddedByTheUser(String username);
}
