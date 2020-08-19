package com.oskarro.muzikum.track;

import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
@CrossOrigin(origins = "https://localhost:4200")
public interface TrackRepository extends JpaRepository<Track, Integer> {

    List<Track> findAll();

    Optional<Track> findById(Integer id);

    Track save(Track track);

    List<Track> findTracksByProviderId(Integer id);

    List<Track> findTracksByGenre(String genre);

    List<Track> findTracksByProviderName(String name);

    List<Track> findTracksByProviderIdAndGenre(Integer id, String genre);

    List<Track> findTracksByPlaylistId(Integer id);

    List<Track> findTracksByVideoId(Integer id);

    void deleteById(Integer id);

    long count();

    Page<Track> findAll(Pageable pageable);

    List<Track> findByGenreOrderByCreatedAtDesc(String genre);

    List<Track> findByUserUsernameOrderByCreatedAtDesc(String username);

    Page<Track> findByGenreOrderByCreatedAtDesc(String genre, Pageable pageable);
}
