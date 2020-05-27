package com.oskarro.muzikum.track;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.playlist.Playlist;
import com.oskarro.muzikum.playlist.PlaylistRepository;
import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.*;

@Service
@Transactional
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;
    private final UserRepository userRepository;

    public TrackServiceImpl(TrackRepository trackRepository, UserRepository userRepository) {
        this.trackRepository = trackRepository;
        this.userRepository = userRepository;
    }
    @Override
    public List<Track> findAll() {
        return trackRepository.findAll();
    }

    @Override
    public Optional<Track> findById(Integer id) {
        return trackRepository.findById(id);
    }

    @Override
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


}
