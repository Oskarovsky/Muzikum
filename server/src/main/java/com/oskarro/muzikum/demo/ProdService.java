package com.oskarro.muzikum.demo;

import com.oskarro.muzikum.article.comment.CommentRepository;
import com.oskarro.muzikum.article.post.Post;
import com.oskarro.muzikum.article.post.PostRepository;
import com.oskarro.muzikum.playlist.PlaylistRepository;
import com.oskarro.muzikum.storage.ArticleImageRepository;
import com.oskarro.muzikum.track.TrackCommentRepository;
import com.oskarro.muzikum.track.TrackFavoriteRepository;
import com.oskarro.muzikum.track.TrackRepository;
import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.user.*;
import com.oskarro.muzikum.user.role.Role;
import com.oskarro.muzikum.user.role.RoleName;
import com.oskarro.muzikum.user.role.RoleRepository;
import com.oskarro.muzikum.video.Category;
import com.oskarro.muzikum.video.Video;
import com.oskarro.muzikum.video.VideoRepository;
import com.oskarro.muzikum.voting.VotingRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Service
public class ProdService {

    public CommentRepository commentRepository;
    public PostRepository postRepository;
    public RoleRepository roleRepository;
    public UserRepository userRepository;
    public PlaylistRepository playlistRepository;
    public VideoRepository videoRepository;
    public TrackRepository trackRepository;
    public TrackFavoriteRepository favoriteTrackRepository;
    public VotingRepository votingRepository;
    public TrackCommentRepository trackCommentRepository;
    public UserStatisticsRepository userStatisticsRepository;
    public ArticleImageRepository articleImageRepository;

    public PasswordEncoder encoder;

    public ProdService(CommentRepository commentRepository,
                       RoleRepository roleRepository,
                       UserRepository userRepository,
                       TrackFavoriteRepository trackFavoriteRepository,
                       VotingRepository votingRepository,
                       PlaylistRepository playlistRepository,
                       VideoRepository videoRepository,
                       UserStatisticsRepository userStatisticsRepository,
                       PostRepository postRepository,
                       TrackRepository trackRepository,
                       TrackCommentRepository trackCommentRepository,
                       ArticleImageRepository articleImageRepository,
                       PasswordEncoder encoder) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
        this.videoRepository = videoRepository;
        this.userStatisticsRepository = userStatisticsRepository;
        this.roleRepository = roleRepository;
        this.commentRepository = commentRepository;
        this.favoriteTrackRepository = trackFavoriteRepository;
        this.articleImageRepository = articleImageRepository;
        this.votingRepository = votingRepository;
        this.trackCommentRepository = trackCommentRepository;
        this.trackRepository = trackRepository;
        this.encoder = encoder;
    }

    public void createInitData() {
        // USER ROLES CREATOR
        Role roleAdmin = new Role();
        roleAdmin.setName(RoleName.ROLE_ADMIN);
        Role rolePm = new Role();
        rolePm.setName(RoleName.ROLE_MODERATOR);
        Role roleUser = new Role();
        roleUser.setName(RoleName.ROLE_USER);
        roleRepository.saveAll(Arrays.asList(roleAdmin, rolePm, roleUser));

        // Creating new user's account
        User userAdmin = User.builder()
                .id(1)
                .username("Oskarro")
                .email("oskar.slyk@gmail.com")
                .password(encoder.encode("123456"))
                .roles(new HashSet<>(Collections.singletonList(roleAdmin)))
                .provider(AuthProvider.local)
                .build();
        User userJacek = User.builder()
                .id(2)
                .username("Jacek")
                .email("jacek@pw.pl")
                .password(encoder.encode("123456"))
                .roles(new HashSet<>(Collections.singletonList(roleUser)))
                .provider(AuthProvider.local)
                .build();
        User userGosia = User.builder()
                .id(3)
                .username("Gosia")
                .email("djoskarro@interia.pl")
                .password(encoder.encode("123456"))
                .roles(new HashSet<>(Collections.singletonList(roleUser)))
                .provider(AuthProvider.local)
                .build();

        userRepository.saveAll(Arrays.asList(userAdmin, userJacek, userGosia));

        // Init user stats
        UserStatistics userStatisticsAdmin = UserStatistics.builder().id(1).user(userAdmin)
                .weekUpload(0).monthUpload(0).totalUpload(0).build();
        UserStatistics userStatisticsJacek = UserStatistics.builder().id(2).user(userJacek)
                .weekUpload(0).monthUpload(0).totalUpload(0).build();
        UserStatistics userStatisticsGosia = UserStatistics.builder().id(3).user(userGosia)
                .weekUpload(0).monthUpload(0).totalUpload(0).build();
        userStatisticsRepository.saveAll(Arrays.asList(userStatisticsAdmin, userStatisticsJacek, userStatisticsGosia));

        // VIDEO PANE:
        Video video1 = Video.builder().id(1).name("Vixa").url("Dp--txMIGPI")
                .category(Category.MIX.toString()).build();
        Video video2 = Video.builder().id(2).name("Virus").url("MpWfj-2P-9M")
                .category(Category.MIX.toString()).build();
        Video video3 = Video.builder().id(3).name("Luna Mix Vol. 9").url("WRooj5n80uo")
                .category(Category.LUNA_MIX.name()).build();
        videoRepository.saveAll(Arrays.asList(video1, video2, video3));

        // TRACKS:
        Track trackVixa = Track.builder().id(1)
                .title("Fun the bass").artist("Tobi").version("Original Mix").genre("VIXA").points(0)
                .user(userAdmin)
                .url("https://krakenfiles.com/view/m3X6949zGK/file.html")
                .urlPlugin("https://s4.krakenfiles.com/getEmbedPlayer/m3X6949zGK?width=1000&autoplay=false&date=08-01-2022")
                .urlSource("KRAKENFILES")
                .build();
        Track trackRetro = Track.builder().id(2)
                .title("Jump To The Base").artist("DJ Cargo").version("Radio Edit").genre("RETRO").points(0)
                .user(userAdmin)
                .url("https://krakenfiles.com/view/lLlRokTgT4/file.html")
                .urlPlugin("https://s2.krakenfiles.com/getEmbedPlayer/lLlRokTgT4?width=1000&autoplay=false&date=25-12-2021")
                .urlSource("KRAKENFILES")
                .build();
        Track trackClub = Track.builder().id(3)
                .title("Tu Es Foutu").artist("In-Grid").version("Extended Remix Mr. Marius 2k22").genre("CLUB").points(0)
                .user(userAdmin)
                .url("https://krakenfiles.com/view/hz9MvrP97H/file.html")
                .urlPlugin("https://s3.krakenfiles.com/getEmbedPlayer/hz9MvrP97H?width=1000&autoplay=false&date=07-01-2022")
                .urlSource("KRAKENFILES")
                .build();
        Track trackTechno = Track.builder().id(4)
                .title("Drum machine").artist("ROBPM").version("Original Mix").genre("TECHNO").points(0)
                .user(userAdmin)
                .url("https://krakenfiles.com/view/uxUuKoCSg0/file.html")
                .urlPlugin("https://s6.krakenfiles.com/getEmbedPlayer/uxUuKoCSg0?width=1000&autoplay=false&date=07-01-2022")
                .urlSource("KRAKENFILES")
                .build();
        Track trackDance = Track.builder().id(5)
                .title("Sunshine").artist("El Damien And Dj Combo").version("Original Mix").genre("DANCE").points(0)
                .user(userAdmin)
                .url("https://krakenfiles.com/view/hXPBFlpmoq/file.html")
                .urlPlugin("https://krakenfiles.com/getEmbedPlayer/hXPBFlpmoq?width=1000&autoplay=false&date=09-01-2022")
                .urlSource("KRAKENFILES")
                .build();
        Track trackDisco = Track.builder().id(6)
                .title("Nasza muzyka").artist("Toples").version("Radio Edit").genre("DISCO").points(0)
                .user(userAdmin)
                .url("https://krakenfiles.com/view/bq7OmgxS3k/file.html")
                .urlPlugin("https://s3.krakenfiles.com/getEmbedPlayer/bq7OmgxS3k?width=1000&autoplay=false&date=20-07-2021")
                .urlSource("KRAKENFILES")
                .build();
        Track trackSet = Track.builder().id(7)
                .title("VIXA").artist("Oskarro").version("MIX").genre("SET").points(0)
                .user(userAdmin)
                .url("https://www17.zippyshare.com/v/n6PPiJHj/file.html")
                .urlPlugin("https://api.zippyshare.com/api/jplayer_embed.jsp?key=n6PPiJHj&server=www17&autostart=true&width=1000")
                .urlSource("ZIPPYSHARE")
                .build();
        trackRepository.saveAll(Arrays.asList(trackVixa, trackRetro, trackClub, trackTechno, trackDance, trackDisco, trackSet));

       // Tracks (oskarro.com)
        Track lunaMix4 = Track.builder()
                .title("Luna Mix Vol. 4").artist("Oskarro & REED And Wesley").version("B-Day Extended Edition").genre("SET").points(0)
                .user(userAdmin)
                .url("https://krakenfiles.com/view/k7dK6XIFYQ/file.html")
                .urlPlugin("https://s4.krakenfiles.com/getEmbedPlayer/k7dK6XIFYQ?width=1000&autoplay=false&date=09-01-2022")
                .urlSource("KRAKENFILES")
                .build();
        Track vinylMix = Track.builder()
                .title("VINYL").artist("Oskarro").genre("SET").points(0)
                .user(userAdmin)
                .url("https://krakenfiles.com/view/4BVV4oOShH/file.html")
                .urlPlugin("https://s2.krakenfiles.com/getEmbedPlayer/4BVV4oOShH?width=1000&autoplay=false&date=09-01-2022")
                .urlSource("KRAKENFILES")
                .build();
        Track virusMix = Track.builder()
                .title("VIRUS").artist("Oskarro").genre("SET").points(0)
                .user(userAdmin)
                .url("https://krakenfiles.com/view/CGV3i8Q4ZL/file.html")
                .urlPlugin("https://s2.krakenfiles.com/getEmbedPlayer/CGV3i8Q4ZL?width=1000&autoplay=false&date=09-01-2022")
                .urlSource("KRAKENFILES")
                .build();

        // Tracks (no domain)
        Track trackNo1 = Track.builder()
                .title("Satisfaction").artist("Benny Benassi").version("MARASS & Ciemny Bootleg").genre("VIXA").points(0)
                .user(userAdmin)
                .url("https://krakenfiles.com/view/5Nr3mOQbmp/file.html")
                .urlPlugin("https://s5.krakenfiles.com/getEmbedPlayer/5Nr3mOQbmp?width=1000&autoplay=false&date=20-08-2021")
                .urlSource("KRAKENFILES")
                .build();

    }

}
