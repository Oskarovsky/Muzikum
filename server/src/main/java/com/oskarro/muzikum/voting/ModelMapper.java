package com.oskarro.muzikum.voting;

import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.voting.model.Poll;
import com.oskarro.muzikum.voting.payload.response.ChoiceResponse;
import com.oskarro.muzikum.voting.payload.response.PollResponse;
import com.oskarro.muzikum.voting.payload.response.UserSummary;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModelMapper {

    /* Mapping the Poll entity to a PollResponse payload */
    public static PollResponse mapPollToPollResponse(Poll poll, Map<Integer, Integer> choiceVotesMap,
                                                     User creator, Long userVote) {
        PollResponse pollResponse = new PollResponse();
        pollResponse.setId(poll.getId());
        pollResponse.setQuestion(poll.getQuestion());
        pollResponse.setCreationDateTime(poll.getCreatedAt());
        pollResponse.setExpirationDateTime(poll.getExpirationDateTime());
        Instant now = Instant.now();
        pollResponse.setIsExpired(poll.getExpirationDateTime().isBefore(now));

        List<ChoiceResponse> choiceResponses = poll.getChoices().stream().map(choice -> {
            ChoiceResponse choiceResponse = new ChoiceResponse();
            choiceResponse.setId(choice.getId());
            choiceResponse.setText(choice.getText());

            choiceResponse.setVoteCount(choiceVotesMap.getOrDefault(choice.getId(), 0));
            return choiceResponse;
        }).collect(Collectors.toList());

        pollResponse.setChoices(choiceResponses);
        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getUsername());
        pollResponse.setCreatedBy(creatorSummary);

        if(userVote != null) {
            pollResponse.setSelectedChoice(userVote);
        }

        long totalVotes = pollResponse.getChoices().stream().mapToLong(ChoiceResponse::getVoteCount).sum();
        pollResponse.setTotalVotes(totalVotes);

        return pollResponse;
    }
}
