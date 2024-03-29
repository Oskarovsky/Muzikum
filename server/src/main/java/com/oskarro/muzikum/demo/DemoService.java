package com.oskarro.muzikum.demo;

import com.oskarro.muzikum.article.comment.CommentRepository;
import com.oskarro.muzikum.article.post.Post;
import com.oskarro.muzikum.article.post.PostRepository;
import com.oskarro.muzikum.playlist.Playlist;
import com.oskarro.muzikum.playlist.PlaylistRepository;
import com.oskarro.muzikum.storage.ArticleImage;
import com.oskarro.muzikum.storage.ArticleImageRepository;
import com.oskarro.muzikum.track.TrackCommentRepository;
import com.oskarro.muzikum.track.TrackFavoriteRepository;
import com.oskarro.muzikum.track.TrackRepository;
import com.oskarro.muzikum.track.model.*;
import com.oskarro.muzikum.user.*;
import com.oskarro.muzikum.user.role.Role;
import com.oskarro.muzikum.user.role.RoleName;
import com.oskarro.muzikum.user.role.RoleRepository;
import com.oskarro.muzikum.video.Category;
import com.oskarro.muzikum.video.Video;
import com.oskarro.muzikum.video.VideoRepository;
import com.oskarro.muzikum.voting.Vote;
import com.oskarro.muzikum.voting.VotingRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Service for creating examples of all basic components and entities
 * It is starting during running application.
 *
 * */

@Service
@AllArgsConstructor
public class DemoService {

    @Value("${user.raw-password}")
    private static final String rawPassword = "123456";

    public final CommentRepository commentRepository;
    public final PostRepository postRepository;
    public final RoleRepository roleRepository;
    public final UserRepository userRepository;
    public final PlaylistRepository playlistRepository;
    public final VideoRepository videoRepository;
    public final TrackRepository trackRepository;
    public final TrackFavoriteRepository favoriteTrackRepository;
    public final VotingRepository votingRepository;
    public final TrackCommentRepository trackCommentRepository;
    public final UserStatisticsRepository userStatisticsRepository;
    public final ArticleImageRepository articleImageRepository;
    public final PasswordEncoder encoder;


    public void createSamples() {
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
                .password(encoder.encode(rawPassword))
                .roles(new HashSet<>(Collections.singletonList(roleAdmin)))
                .provider(AuthProvider.local)
                .build();
        User userJacek = User.builder()
                .id(2)
                .username("Jacek")
                .email("jacek@pw.pl")
                .password(encoder.encode(rawPassword))
                .roles(new HashSet<>(Collections.singletonList(roleUser)))
                .provider(AuthProvider.local)
                .build();
        User userGosia = User.builder()
                .id(3)
                .username("Gosia")
                .email("djoskarro@interia.pl")
                .password(encoder.encode(rawPassword))
                .roles(new HashSet<>(Collections.singletonList(roleUser)))
                .provider(AuthProvider.local)
                .build();

        userRepository.saveAll(Arrays.asList(userAdmin, userJacek, userGosia));

        // Init user stats
        UserStatistics userStatisticsAdmin = UserStatistics.builder().id(1).user(userAdmin)
                .weekUpload(0).monthUpload(0).totalUpload(0).build();
        UserStatistics userStatisticsJacek = UserStatistics.builder().id(2).user(userJacek)
                .weekUpload(0).monthUpload(0).totalUpload(0).build();
        userStatisticsRepository.saveAll(Arrays.asList(userStatisticsAdmin, userStatisticsJacek));


        // PLAYLIST CREATING
        Playlist playlist = Playlist.builder().id(1).name("MyTop").user(userAdmin).views(10).build();
        Playlist playlist2 = Playlist.builder().id(2).name("SecondTop").user(userJacek).build();
        playlistRepository.saveAll(Arrays.asList(playlist, playlist2));


        // VIDEO PANE:
        Video video2 = Video.builder().id(2).name("Virus").url("MpWfj-2P-9M")
                .category(Category.MIX.toString()).build();
        Video video3 = Video.builder().id(3).name("Luna Mix Vol. 9").url("WRooj5n80uo")
                .category(Category.LUNA_MIX.name()).build();
        videoRepository.saveAll(Arrays.asList(video2, video3));

        // FULL VIDEO PANEL
        Playlist playlistVixa = Playlist.builder().id(3).name("VixaTracklist").user(userAdmin).build();
        playlistRepository.save(playlistVixa);
        Video videoVixa = Video.builder().id(1).name("Vixa").url("Dp--txMIGPI")
                .category(Category.MIX.toString()).playlist(playlistVixa).build();
        videoRepository.save(videoVixa);

        // POSTS
        Post postFirst = Post.builder()
                .title("Otwarcie nowej strony")
                .description("Opis wszystkich opcji dostępnych na stronie")
                .content("Dostępnych jest wiele nowych super rzeczy, które są idealne dla fanów muzyki klubowej")
                .user(userAdmin)
                .build();
        postRepository.save(postFirst);

        try {
            File firstArticleImage = new File("/home/oskarro/Developer/Projects/javaProjects/oskarro.com/uploads/article/oslyko_banner_type.png");
            ArticleImage articleImage = ArticleImage.builder()
                    .articleId(1)
                    .name("oslyko_banner_type.png")
                    .pic(Files.readAllBytes(firstArticleImage.toPath()))
                    .build();
            articleImageRepository.save(articleImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Post postSecond = Post.builder()
                .title("Ciąg dalszy nastąpi...")
                .description("Sprawdzenie zawartości drugiego posta, który powinien pojawić się na stronie głównej")
                .content("Dostępnych jest wiele nowych super rzeczy, które są idealne dla fanów muzyki klubowej")
                .user(userAdmin)
                .build();
        postRepository.save(postSecond);


        // TRACK
        Track track1 = Track.builder().id(3).title("Nitro pleasure").artist("Mango Bordex")
                .version("Radio edit").playlist(playlist).build();
        Track track2 = Track.builder().id(5).title("Marco Polo").artist("DJ Test")
                .version("Radio edit").playlist(playlist).build();
        Track track3 = Track.builder().id(7).title("Test Song").artist("Bauer")
                .version("Extended edit").playlist(playlist2).build();
        Track track4 = Track.builder().id(1).title("One Night Test").artist("Team Test")
                .version("Tester Remix").playlist(playlist2).build();
        Track track5 = Track.builder().id(4).title("Travel to test").artist("Tester")
                .version("Original Mix").playlist(playlist).build();
        Track track6 = Track.builder().title("L'Italiano").artist("The Sicilians ft. Angelo Venuto")
                .version("The DJ Serg Remix").url("https://www.youtube.com/watch?v=hymoFuKK_Ac").playlist(playlist).build();
        Track track7 = Track.builder().title("Organic Mock").artist("DJ Valdo")
                .version("Cabro Remix").playlist(playlist).build();
        Track track8 = Track.builder().title("God is tester").artist("Clubber")
                .version("Mash Up").playlist(playlist).build();
        Track track12 = Track.builder().title("We love it").artist("Dj Shogun")
                .version("Original Mix").points(0).genre("VIXA").build();

        trackRepository.saveAll(
                Arrays.asList(track1, track2, track3, track6, track7,
                        track8, track4, track5, track12)
        );

        Vote vote1 = Vote.builder().user(userAdmin).track(track4).build();
        Vote vote2 = Vote.builder().user(userJacek).track(track4).build();
        Vote vote3 = Vote.builder().user(userAdmin).track(track6).build();
        votingRepository.saveAll(Arrays.asList(vote1, vote2, vote3));

        TrackFavorite favoriteTrack001 = TrackFavorite.builder().track(track6).user(userAdmin).build();
        TrackFavorite favoriteTrack002 = TrackFavorite.builder().track(track2).user(userAdmin).build();
        TrackFavorite favoriteTrack003 = TrackFavorite.builder().track(track8).user(userAdmin).build();
        favoriteTrackRepository.saveAll(Arrays.asList(favoriteTrack001, favoriteTrack002, favoriteTrack003));

        Track popularTrackRetro = Track.builder()
                .artist("Sal De Sol & Topmodelz")
                .title("Maniac")
                .version("Pulsedriver Extended Remix")
                .genre(Genre.CLUB.toString())
                .points(2)
                .urlSource(UrlSource.KRAKENFILES.name())
                .url("https://krakenfiles.com/view/ttkeGqDrNS/file.html")
                .build();
        Track popularTrackClub = Track.builder()
                .artist("Max Fail, Blaikz, Paul Keen")
                .title("Désenchantée")
                .version("Extended Mix")
                .genre(Genre.VIXA.toString())
                .urlSource(UrlSource.KRAKENFILES.name())
                .url("https://krakenfiles.com/view/EgvETCKq1r/file.html")
                .points(4)
                .build();
        Track popularTrackDance = Track.builder()
                .artist("Skrillex, Fred Again.. & Flowdan")
                .title("Rumble")
                .version("Henry Fong Remix")
                .urlSource(UrlSource.KRAKENFILES.name())
                .url("https://krakenfiles.com/view/ktEML71HMw/file.html")
                .genre(Genre.TECHNO.toString())
                .points(3)
                .build();
        Track popularTrackTechno = Track.builder()
                .artist("Oskarro")
                .title("Retro Vixa vol. 5")
                .version("2023")
                .urlSource(UrlSource.KRAKENFILES.name())
                .url("https://krakenfiles.com/view/it3Y63Wl1b/file.html")
                .genre(Genre.SET.toString())
                .points(2)
                .build();
        Track popularTrackHouse = Track.builder()
                .artist("Pulsedriver, Steve Modana")
                .title("Kick Da Nation")
                .version("Radio Mix")
                .urlSource(UrlSource.KRAKENFILES.name())
                .url("https://krakenfiles.com/view/C5Ww1wv7v3/file.html")
                .genre(Genre.DANCE.toString())
                .points(2)
                .build();
        Track popularVixa = Track.builder()
                .artist("Oskarro")
                .title("Retro Vixa vol 4")
                .version("2023")
                .urlSource(UrlSource.KRAKENFILES.name())
                .url("https://krakenfiles.com/view/amftrE1aK9/file.html")
                .genre(Genre.SET.toString())
                .points(5)
                .build();
        Track popularDance = Track.builder()
                .artist("Dennis Sheperd, Diandra Faye")
                .title("Yesterday Is Gone")
                .version("Extended Mix")
                .urlSource(UrlSource.KRAKENFILES.name())
                .url("https://krakenfiles.com/view/Ukcjdj4YR5/file.html")
                .genre(Genre.DANCE.toString())
                .points(4)
                .build();
        Track popularVixa2 = Track.builder()
                .artist("Malik Montana x DaChoyce")
                .title("Jetlag")
                .version("PaT MaT Brothers Bootleg")
                .urlSource(UrlSource.KRAKENFILES.name())
                .url("https://krakenfiles.com/view/tp6Bgxmz3W/file.html")
                .genre(Genre.DANCE.toString())
                .points(1)
                .build();
        Track popularRetro = Track.builder()
                .artist("DJ 2M!C")
                .title("To Be Love")
                .version("Club Mix")
                .urlSource(UrlSource.KRAKENFILES.name())
                .url("https://krakenfiles.com/view/pa00HvTgTu/file.html")
                .genre(Genre.RETRO.toString())
                .points(5)
                .build();

        trackRepository.saveAll(Arrays.asList(popularTrackClub, popularTrackDance, popularTrackHouse,
                popularTrackRetro, popularTrackTechno, popularVixa, popularDance, popularVixa2, popularRetro));


        Track userTrack1 = Track.builder().artist("Hitman").title("One River").version("Original Mix")
                .genre(Genre.TECHNO.toString()).points(1).user(userAdmin).build();
        Track userTrack2 = Track.builder().artist("OneSound").title("Magician Dream").version("Dave Aude Mix")
                .genre(Genre.SET.toString()).points(3).user(userAdmin).build();
        Track userTrack3 = Track.builder().artist("BeatBoxer").title("Night Our").version("Vinyl Edit")
                .genre(Genre.DISCO.toString()).points(1).user(userAdmin).build();

        trackRepository.saveAll(Arrays.asList(userTrack1, userTrack2, userTrack3));

        TrackComment trackComment1 =
                TrackComment.builder().track(popularTrackClub).text("Super hit").user(userAdmin).build();
        TrackComment trackComment2 =
                TrackComment.builder().track(popularTrackClub).text("Jest fajnie").user(userJacek).build();
        trackCommentRepository.saveAll(Arrays.asList(trackComment1, trackComment2));

    }

}
