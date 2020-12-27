package com.oskarro.muzikum.service;

import com.oskarro.muzikum.track.TrackCommentRepository;
import com.oskarro.muzikum.track.TrackRepository;
import com.oskarro.muzikum.track.TrackService;
import com.oskarro.muzikum.track.model.Genre;
import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.track.model.TrackComment;
import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.user.UserRepository;
import com.oskarro.muzikum.user.role.Role;
import com.oskarro.muzikum.user.role.RoleName;
import com.oskarro.muzikum.user.role.RoleRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@ExtendWith(SpringExtension.class)
@Import(com.oskarro.muzikum.config.TestConfiguration.class)
@RunWith(SpringRunner.class)
@SpringBootTest
//@Ignore
@Transactional
public class TrackServiceTest {

    @Autowired
    TrackService trackService;

    @Autowired
    TrackCommentRepository trackCommentRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TrackRepository trackRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void test_getAllTrackCommentsByTrackId() {
        Role roleAdmin = new Role();
        roleAdmin.setName(RoleName.ROLE_ADMIN);
        roleRepository.save(roleAdmin);
        User userAdmin = User.builder().id(1).username("Oskarro").email("oskar.slyk@gmail.com")
                .password("111111").roles(new HashSet<>(Collections.singletonList(roleAdmin))).build();
        userRepository.save(userAdmin);
        Track popularTrackClub = Track.builder().artist("Oskarro").title("Vixologia").version("Extended Mix")
                .genre(Genre.CLUB.toString()).points(4).build();
        trackRepository.save(popularTrackClub);
        TrackComment trackComment1 =
                TrackComment.builder().id(2222).track(popularTrackClub).text("Super hit").user(userAdmin).build();
        TrackComment trackComment2 =
                TrackComment.builder().track(popularTrackClub).text("Jest fajnie").user(userAdmin).build();
        trackCommentRepository.saveAll(Arrays.asList(trackComment1, trackComment2));
        System.out.println(trackService.getAllTrackCommentsByTrackId(popularTrackClub.getId()));
    }

}
