package com.oskarro.muzikum.voting;

import com.oskarro.muzikum.voting.model.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT NEW com.oskarro.muzikum.voting.ChoiceVoteCount(v.choice.id, count(v.id)) FROM Vote v WHERE v.poll.id in :pollIds GROUP BY v.choice.id")
    List<ChoiceVoteCount> countByPollIdInGroupByChoiceId(@Param("pollIds") List<Integer> pollIds);

    @Query("SELECT NEW com.oskarro.muzikum.voting.ChoiceVoteCount(v.choice.id, count(v.id)) FROM Vote v WHERE v.poll.id = :pollId GROUP BY v.choice.id")
    List<ChoiceVoteCount> countByPollIdGroupByChoiceId(@Param("pollId") Integer pollId);

    @Query("SELECT v FROM Vote v where v.user.id = :userId and v.poll.id in :pollIds")
    List<Vote> findByUserIdAndPollIdIn(@Param("userId") Integer userId, @Param("pollIds") List<Integer> pollIds);

    @Query("SELECT v FROM Vote v where v.user.id = :userId and v.poll.id = :pollId")
    Vote findByUserIdAndPollId(@Param("userId") Integer userId, @Param("pollId") Integer pollId);

    @Query("SELECT COUNT(v.id) from Vote v where v.user.id = :userId")
    long countByUserId(@Param("userId") Integer userId);

    @Query("SELECT v.poll.id FROM Vote v WHERE v.user.id = :userId")
    Page<Integer> findVotedPollIdsByUserId(@Param("userId") Integer userId, Pageable pageable);
}
