package com.oskarro.muzikum.track;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;

    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
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

}
