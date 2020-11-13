package com.oskarro.muzikum.repository;

import com.oskarro.muzikum.track.TrackCommentRepository;
import com.oskarro.muzikum.track.TrackRepository;
import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.track.model.TrackComment;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest("hibernate.query.interceptor.error-level=ERROR")
@Transactional
@Ignore
public class NPlusOneQueriesLoggingTest {

    @Autowired
    TrackRepository trackRepository;

    @Autowired
    TrackCommentRepository trackCommentRepository;

    // Fetch the tracks without the users
    @Test
    public void nPlusOneQueriesDetection_isLoggingWhenDetectingNPlusOneQueries() {
        List<Track> tracks = trackRepository.findAll();

        List<String> names = tracks.stream()
                .filter(mes -> mes.getProvider()!=null)
                .map(message -> message.getTitle() + " " + message.getProvider().getName())
                .collect(Collectors.toList());

        List<TrackComment> comments = trackCommentRepository.findAll();
        List<String> names2 = comments.stream()
                .map(message -> message.getTrack().getTitle())
                .collect(Collectors.toList());
    }
}
