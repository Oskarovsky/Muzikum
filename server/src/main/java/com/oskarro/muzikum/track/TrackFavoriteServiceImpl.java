package com.oskarro.muzikum.track;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.track.model.TrackFavorite;
import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TrackFavoriteServiceImpl implements TrackFavoriteService {

    private final UserRepository userRepository;
    private final TrackRepository trackRepository;
    private final TrackFavoriteRepository favoriteTrackRepository;

    public TrackFavoriteServiceImpl(UserRepository userRepository,
                                    TrackRepository trackRepository,
                                    TrackFavoriteRepository favoriteTrackRepository) {
        this.userRepository = userRepository;
        this.trackRepository = trackRepository;
        this.favoriteTrackRepository = favoriteTrackRepository;
    }

    @Override
    public void addTrackToFavorite(Integer trackId, String username) {
        Optional<TrackFavorite> favoriteTrackDemo =
                favoriteTrackRepository.findFavoriteTrackByTrackIdAndUserUsername(trackId, username);
        if (favoriteTrackDemo.isPresent()) {
            log.warn("You can add track to favorites only once");
        } else {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
            Track track = trackRepository.findById(trackId)
                    .orElseThrow(() -> new ResourceNotFoundException("Track", "trackId", trackId));
            TrackFavorite favoriteTrack = TrackFavorite.builder().track(track).user(user).build();

            if (track.getPoints() == null) {
                track.setPoints(1);
            } else {
                track.setPoints(track.getPosition() + 1);
            }
            user.setFavoriteTrack(track);
            trackRepository.save(track);
            favoriteTrackRepository.save(favoriteTrack);
        }
    }

    @Override
    public List<TrackFavorite> getFavoriteTracksByUsername(final String username) {
        return favoriteTrackRepository.findFavoriteTracksByUserUsername(username);
    }
}
