package com.oskarro.muzikum.playlist;

import com.oskarro.muzikum.track.Track;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class PlaylistServiceImpl implements PlaylistService {

    PlaylistRepository playlistRepository;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public List<Playlist> getAllPlaylist() {
        return playlistRepository.findAll();
    }

    @Override
    public Optional<Playlist> findPlaylistById(Integer id) {
        return playlistRepository.findById(id);
    }

    @Override
    public Optional<Playlist> findPlaylistByName(String name) {
        return playlistRepository.findByName(name);
    }

/*    @Override
    public void addTrackToPlaylist(Track track, Integer id) {
        findPlaylistById(id)
                .ifPresent(playlist -> playlist.getTracks().add(track));
    }

    @Override
    public void removeTrackFromPlaylist(Track track, Integer id) {
        findPlaylistById(id)
                .ifPresent(playlist -> playlist.getTracks().remove(track));
    }*/

    @Override
    public void addPlaylist(Playlist playlist) {
        playlistRepository.save(playlist);
    }

    @Override
    public Playlist updatePlaylist(Playlist playlist, Integer id) {
//        Playlist playlist1 = findPlaylistById()
        return null;
    }

    @Override
    public void deletePlaylistById(Integer id) {
        playlistRepository.deleteById(id);
    }


}
