package com.oskarro.muzikum.track;

import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.track.model.TrackComment;
import com.oskarro.muzikum.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
@CrossOrigin
public interface TrackRepository extends JpaRepository<Track, Integer> {

    List<Track> findAll();

    Optional<Track> findById(Integer id);

    Track save(Track track);

    List<Track> findTracksByGenre(String genre);

    List<Track> findTracksByPlaylistId(Integer id);

    List<Track> findTracksByVideoId(Integer id);

    void deleteById(Integer id);

    long count();

    Page<Track> findAll(Pageable pageable);

    List<Track> findByGenreOrderByCreatedAtDesc(String genre);

    List<Track> findByUserUsernameOrderByCreatedAtDesc(String username);

    Page<Track> findByGenreAndUrlSourceIsNotNullOrderByCreatedAtDesc(String genre, Pageable pageable);

    Page<Track> findByUserUsernameOrderByCreatedAtDesc(String username, Pageable pageable);

    @Query("SELECT COUNT(t) FROM Track t WHERE t.user.username=?1")
    long countTracksByUserUsername(String username);

}
