package com.oskarro.muzikum.demo;

import com.oskarro.muzikum.article.comment.CommentRepository;
import com.oskarro.muzikum.article.post.Post;
import com.oskarro.muzikum.article.post.PostRepository;
import com.oskarro.muzikum.crawler.CrawlerService;
import com.oskarro.muzikum.playlist.Playlist;
import com.oskarro.muzikum.playlist.PlaylistRepository;
import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.provider.ProviderRepository;
import com.oskarro.muzikum.provider.contractor.BillboardService;
import com.oskarro.muzikum.provider.contractor.RadiopartyService;
import com.oskarro.muzikum.track.TrackCommentRepository;
import com.oskarro.muzikum.track.TrackRepository;
import com.oskarro.muzikum.track.model.Genre;
import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.track.model.TrackComment;
import com.oskarro.muzikum.track.model.UrlSource;
import com.oskarro.muzikum.user.*;
import com.oskarro.muzikum.user.favorite.FavoriteTrack;
import com.oskarro.muzikum.user.favorite.FavoriteTrackRepository;
import com.oskarro.muzikum.user.role.Role;
import com.oskarro.muzikum.user.role.RoleName;
import com.oskarro.muzikum.user.role.RoleRepository;
import com.oskarro.muzikum.video.Category;
import com.oskarro.muzikum.video.Video;
import com.oskarro.muzikum.video.VideoRepository;
import com.oskarro.muzikum.voting.Vote;
import com.oskarro.muzikum.voting.VotingRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DemoService {

    public CommentRepository commentRepository;
    public PostRepository postRepository;
    public RoleRepository roleRepository;
    public UserRepository userRepository;
    public PlaylistRepository playlistRepository;
    public VideoRepository videoRepository;
    public TrackRepository trackRepository;
    public ProviderRepository providerRepository;
    public FavoriteTrackRepository favoriteTrackRepository;
    public VotingRepository votingRepository;
    public TrackCommentRepository trackCommentRepository;
    public UserStatisticsRepository userStatisticsRepository;

    public CrawlerService crawlerService;
    public BillboardService billboardService;
    public RadiopartyService radiopartyService;

    public PasswordEncoder encoder;

    public DemoService(CommentRepository commentRepository, RoleRepository roleRepository,
                       UserRepository userRepository, FavoriteTrackRepository favoriteTrackRepository,
                       VotingRepository votingRepository, ProviderRepository providerRepository,
                       PlaylistRepository playlistRepository, VideoRepository videoRepository,
                       UserStatisticsRepository userStatisticsRepository, PostRepository postRepository,
                       TrackRepository trackRepository, TrackCommentRepository trackCommentRepository,
                       CrawlerService crawlerService, RadiopartyService radiopartyService,
                       BillboardService billboardService,
                       PasswordEncoder encoder) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
        this.videoRepository = videoRepository;
        this.providerRepository = providerRepository;
        this.userStatisticsRepository = userStatisticsRepository;
        this.roleRepository = roleRepository;
        this.commentRepository = commentRepository;
        this.favoriteTrackRepository = favoriteTrackRepository;
        this.votingRepository = votingRepository;
        this.trackCommentRepository = trackCommentRepository;
        this.trackRepository = trackRepository;
        this.crawlerService = crawlerService;
        this.billboardService = billboardService;
        this.radiopartyService = radiopartyService;
        this.encoder = encoder;
    }

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
        userStatisticsRepository.saveAll(Arrays.asList(userStatisticsAdmin, userStatisticsJacek));


        // PLAYLIST CREATING
        Playlist playlist = Playlist.builder().id(1).name("MyTop").user(userAdmin).views(10).build();
        Playlist playlist2 = Playlist.builder().id(2).name("SecondTop").user(userJacek).build();
        playlistRepository.saveAll(Arrays.asList(playlist, playlist2));


        // VIDEO PANE:
        Video video1 = Video.builder().id(1).name("Vixa").url("Dp--txMIGPI")
                .category(Category.MIX.toString()).playlist(playlist).build();
        Video video2 = Video.builder().id(2).name("Virus").url("MpWfj-2P-9M")
                .category(Category.MIX.toString()).playlist(playlist2).build();
        Video video3 = Video.builder().id(3).name("L'Italiano").url("moFuKK_Ac")
                .category(Category.RETRO.name()).build();
        Video video4 = Video.builder().id(4).name("Luna Mix Vol. 9").url("WRooj5n80uo")
                .category(Category.LUNA_MIX.name()).build();
        videoRepository.saveAll(Arrays.asList(video1, video2, video3, video4));


        // POSTS
        Post postFirst = Post.builder().
                title("Otwarcie nowej strony")
                .description("Opis wszystkich opcji dostępnych na stronie")
                .content("Dostępnych jest wiele nowych super rzeczy, które są idealne dla fanów muzyki klubowej")
                .user(userAdmin)
                .build();
        postRepository.save(postFirst);

        Post postSecond = Post.builder().
                title("Ciąg dalszy nastąpi...")
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
        Track track9 = Track.builder().title("First shit title").artist("Med")
                .version("dsd edit").video(video1).build();
        Track track10 = Track.builder().title("One Air").artist("Mayoman")
                .version("Original Mix").video(video2).build();
        Track track11 = Track.builder().title("one tow three").artist("test")
                .version("Original Mix").video(video2).build();
        Track track12 = Track.builder().title("We love it").artist("Dj Shogun")
                .version("Original Mix").points(0).genre("VIXA").build();

        trackRepository.saveAll(
                Arrays.asList(
                        track1, track2, track3, track6, track7, track8, track9,
                        track10, track11, track4, track5, track12)
        );

        Vote vote1 = Vote.builder().user(userAdmin).track(track9).build();
        Vote vote2 = Vote.builder().user(userJacek).track(track10).build();
        Vote vote3 = Vote.builder().user(userAdmin).track(track11).build();
        votingRepository.saveAll(Arrays.asList(vote1, vote2, vote3));

        FavoriteTrack favoriteTrack001 = FavoriteTrack.builder().track(track9).user(userAdmin).build();
        FavoriteTrack favoriteTrack002 = FavoriteTrack.builder().track(track10).user(userAdmin).build();
        FavoriteTrack favoriteTrack003 = FavoriteTrack.builder().track(track11).user(userAdmin).build();
        favoriteTrackRepository.saveAll(Arrays.asList(favoriteTrack001, favoriteTrack002, favoriteTrack003));

        Track popularTrackRetro = Track.builder()
                .artist("Scooter")
                .title("Maria (I like it loud)")
                .version("Original Mix")
                .genre(Genre.RETRO.toString())
                .points(2)
                .urlSource(UrlSource.KRAKENFILES.name())
                .url("https://krakenfiles.com/view/OmdojlqSnV/file.html")
                .build();
        Track popularTrackClub = Track.builder()
                .artist("Oskarro")
                .title("Vixologia")
                .version("Extended Mix")
                .genre(Genre.CLUB.toString())
                .urlSource(UrlSource.ZIPPYSHARE.name())
                .url("https://www44.zippyshare.com/v/InBKmpoy/file.html")
                .points(4)
                .build();
        Track popularTrackDance = Track.builder()
                .artist("Danya")
                .title("My Love")
                .version("Ozi Remix")
                .urlSource(UrlSource.KRAKENFILES.name())
                .url("https://krakenfiles.com/view/LYsGyhpfGa/file.html")
                .genre(Genre.DANCE.toString())
                .points(3)
                .build();
        Track popularTrackTechno = Track.builder()
                .artist("Bumps")
                .title("O shit!")
                .version("Original Mix")
                .urlSource(UrlSource.KRAKENFILES.name())
                .url("https://krakenfiles.com/view/j9PeY5Qk7t/file.html")
                .genre(Genre.TECHNO.toString())
                .points(2)
                .build();
        Track popularTrackHouse = Track.builder()
                .artist("Calian")
                .title("Summer ending")
                .version("Radio Mix")
                .urlSource(UrlSource.KRAKENFILES.name())
                .url("https://krakenfiles.com/view/j9PeY5Qk7t/file.html")
                .genre(Genre.HOUSE.toString())
                .points(5)
                .build();

        trackRepository.saveAll(Arrays.asList(popularTrackClub, popularTrackDance, popularTrackHouse,
                popularTrackRetro, popularTrackTechno));


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

    public void createProviders() {
        // GENRE COLLECTIONS FOR PROVIDERS
        List<Genre> radiopartyGenres = Stream.of(Genre.CLUB).collect(Collectors.toList());
        List<Genre> billboardGenres = Stream.of(Genre.DANCE).collect(Collectors.toList());

        // DEFAULT PROVIDERS

        providerRepository.saveAll(Arrays.asList(
                Provider.builder().id(2).description("very nice").url("https://radioparty.pl/partylista.html").
                        genres(radiopartyGenres).name("radioparty").build(),
                Provider.builder().id(4).description("beautiful").url("https://www.billboard.com/charts/year-end/2019/dance-club-songs")
                        .genres(billboardGenres).name("billboard").build()
        ));

        Optional<Provider> radiopartyProvider = providerRepository.findById(2);
        Optional<Provider> billboardProvider = providerRepository.findById(4);

        Provider provider = Provider.builder()
                .id(8).description("tasty service").url("https://music.apple.com/").name("apple").build();
        crawlerService.parseWeb(provider);

        // TRACKS FETCHING FROM EXTERNAL SERVICES
        radiopartyProvider.map(radiopartyService::getTrackList);
        billboardProvider.map(billboardService::getTrackList);

    }

}
