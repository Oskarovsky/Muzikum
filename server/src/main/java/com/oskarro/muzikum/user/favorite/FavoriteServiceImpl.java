package com.oskarro.muzikum.user.favorite;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.track.TrackRepository;
import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class FavoriteServiceImpl implements FavoriteService {

    UserRepository userRepository;
    TrackRepository trackRepository;
    FavoriteTrackRepository favoriteTrackRepository;

    public FavoriteServiceImpl(UserRepository userRepository, TrackRepository trackRepository,
                               FavoriteTrackRepository favoriteTrackRepository) {
        this.userRepository = userRepository;
        this.trackRepository = trackRepository;
        this.favoriteTrackRepository = favoriteTrackRepository;
    }

    @Override
    public void addTrackToFavorite(Integer trackId, String username) {
        Optional<FavoriteTrack> favoriteTrackDemo =
                favoriteTrackRepository.findFavoriteTrackByTrackIdAndUserUsername(trackId, username);
        if (favoriteTrackDemo.isPresent()) {
            log.warn("You can add track to favorites only once");
        } else {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
            Track track = trackRepository.findById(trackId)
                    .orElseThrow(() -> new ResourceNotFoundException("Track", "trackId", trackId));
            FavoriteTrack favoriteTrack = FavoriteTrack.builder().track(track).user(user).build();
            if (track.getPosition() == null) {
                track.setPoints(1);
            } else {
                track.setPoints(track.getPosition() + 1);
            }
            user.setFavoriteTrack(track);
            trackRepository.save(track);
            favoriteTrackRepository.save(favoriteTrack);
        }

    }
}
