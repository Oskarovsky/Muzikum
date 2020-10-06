package com.oskarro.muzikum.track;

import com.oskarro.muzikum.track.model.TrackComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource
@CrossOrigin(origins = "https://localhost:4200")
public interface TrackCommentRepository extends JpaRepository<TrackComment, Integer> {

    List<TrackComment> findTrackCommentsByTrackIdOrderByCreatedAtDesc(Integer trackId);

    void deleteTrackCommentById(Integer commentId);

}
