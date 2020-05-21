package com.oskarro.muzikum.voting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource
@CrossOrigin(origins = "https://localhost:4200")
public interface VotingRepository extends JpaRepository<Vote, Integer> {

    List<Vote> findVotesByTrackId(Integer trackId);

    List<Vote> findVotesByUserUsername(String username);

    Vote save(Vote vote);
}
