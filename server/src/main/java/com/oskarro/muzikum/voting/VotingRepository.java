package com.oskarro.muzikum.voting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotingRepository extends JpaRepository<Vote, Integer> {

    List<Vote> findVotesByTrackId(Integer trackId);

    List<Vote> findVotesByUserUsername(String username);

    Optional<Vote> findVotesByTrackIdAndUserUsername(Integer trackId, String username);

    Vote save(Vote vote);
}
