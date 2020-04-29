package com.oskarro.muzikum.voting;

import com.oskarro.muzikum.voting.model.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PollRepository extends JpaRepository<Poll, Integer> {

    Optional<Poll> findById(Integer pollId);

    Page<Poll> findByCreatedBy(Integer userId, Pageable pageable);

    long countByCreatedBy(Integer userId);

    List<Poll> findByIdIn(List<Integer> pollIds);

    List<Poll> findByIdIn(List<Integer> pollIds, Sort sort);

}
