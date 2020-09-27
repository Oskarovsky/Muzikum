package com.oskarro.muzikum.track;

import com.oskarro.muzikum.track.model.TrackComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackCommentRepository extends JpaRepository<TrackComment, Integer> {

    List<TrackComment> findTrackCommentsByTrackIdOrderByCreatedAtDesc(Integer trackId);

}
