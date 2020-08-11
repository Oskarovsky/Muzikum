package com.oskarro.muzikum.track;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class TrackServiceImpl implements TrackService {

    @Autowired
    private TrackDaoImpl dao;

    private final TrackRepository trackRepository;
    private final UserRepository userRepository;

    public TrackServiceImpl(TrackRepository trackRepository, UserRepository userRepository) {
        super();
        this.trackRepository = trackRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Track> findAll() {
        return trackRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Track> findById(Integer id) {
        return trackRepository.findById(id);
    }

    @Override
    @Transactional
    public Track saveTrack(Track track) {
        return trackRepository.save(track);
    }

    @Override
    public List<Track> findByProviderId(Integer id) {
        return trackRepository.findTracksByProviderId(id);
    }

    @Override
    public List<Track> findTracksByGenre(String genre) {
        return trackRepository.findTracksByGenre(genre.toUpperCase());
    }

    @Override
    public List<Track> findByProviderIdAndGenre(Integer id, String genre) {
        return trackRepository.findTracksByProviderIdAndGenre(id, genre.toUpperCase());
    }

    @Override
    public List<Track> findTracksByProviderName(String name) {
        return trackRepository.findTracksByProviderName(name);
    }

    @Override
    public List<Track> findAllTracksFromPlaylist(Integer id) {
        return new ArrayList<>(trackRepository.findTracksByPlaylistId(id));
    }

    @Override
    public List<Track> findAllTracksFromVideo(Integer id) {
        return new ArrayList<>(trackRepository.findTracksByVideoId(id));
    }

    @Override
    public List<Track> findRandomTracksByProviderIdAndGenre(Integer id, String genre, int numberOfTracks) {
        Random rand = new Random();
        List<Track> fetchedTracks = trackRepository.findTracksByProviderIdAndGenre(id, genre.toUpperCase());
        if (fetchedTracks.size() == 0) {
            return new ArrayList<>();
        }
        return rand
                .ints(numberOfTracks, 0, fetchedTracks.size())
                .mapToObj(fetchedTracks::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Track> findRandomTracksByProviderForAllGenres(Integer providerId, int numberOfTracks) {
        return Stream.of(
                findRandomTracksByProviderIdAndGenre(providerId, "CLUB", numberOfTracks),
                findRandomTracksByProviderIdAndGenre(providerId, "RETRO", numberOfTracks),
                findRandomTracksByProviderIdAndGenre(providerId, "HOUSE", numberOfTracks),
                findRandomTracksByProviderIdAndGenre(providerId, "TECHNO", numberOfTracks),
                findRandomTracksByProviderIdAndGenre(providerId, "DANCE", numberOfTracks)                )
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Track getRandomTrack() {
        long tracksQuantity = trackRepository.count();
        int index = (int) (Math.random() * tracksQuantity);
        Page<Track> trackPage = trackRepository.findAll(PageRequest.of(index, 1));
        Track track = null;
        if (trackPage.hasContent()) {
            track = trackPage.getContent().get(0);
        }
        return track;
    }

    @Override
    public void addTrackToFavorite(Integer trackId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new ResourceNotFoundException("Track", "trackId", trackId));
        if (track.getPosition() == null) {
            track.setPoints(1);
        } else {
            track.setPoints(track.getPosition() + 1);
        }
        user.setFavoriteTrack(track);
        userRepository.save(user);
        trackRepository.save(track);
    }

    @Override
    public Track getMostPopularTrackByGenre(String genre) {
        List<Track> allTracksByGenre = findTracksByGenre(genre);
        return allTracksByGenre
                .stream()
                .filter(track -> track.getPoints() != null)
                .max(Comparator.comparing(Track::getPoints))
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Track> getListOfMostPopularTracksByGenre(String genre, Integer numberOfTracks) {
        List<Track> allTracksByGenre = findTracksByGenre(genre);

        return allTracksByGenre.stream()
                .filter(track -> track.getPoints() != null)
                .sorted(Comparator.comparingInt(Track::getPoints).reversed())
                .limit(numberOfTracks)
                .collect(Collectors.toList());
    }

    @Override
    public List<Track> getLastAddedTracksByGenre(String genre, Integer numberOfTracks) {
        List<Track> fetchedTracks = trackRepository.findByGenreOrderByCreatedAtDesc(genre.toUpperCase());
        return fetchedTracks
                .stream()
                .limit(numberOfTracks)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Track> getLastAddedTracksByUsername(String username, Integer numberOfTracks) {
        List<Track> fetchedTracks = trackRepository.findByUserUsernameOrderByCreatedAtDesc(username);
        return fetchedTracks
                .stream()
                .limit(numberOfTracks)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Track> getLastAddedTracksByGenreOnlyWithUser(String genre, Integer numberOfTracks) {
        List<Track> fetchedTracks = trackRepository.findByGenreOrderByCreatedAtDesc(genre.toUpperCase());
        return fetchedTracks
                .stream()
                .limit(numberOfTracks)
                .filter(Objects::nonNull)
                .filter(track -> track.getUser() != null)
                .collect(Collectors.toList());
    }




}
