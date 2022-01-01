package com.oskarro.muzikum.track;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.plugin.PluginKrakenResponse;
import com.oskarro.muzikum.plugin.PluginService;
import com.oskarro.muzikum.storage.Cover;
import com.oskarro.muzikum.storage.CoverRepository;
import com.oskarro.muzikum.storage.ImageRepository;
import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.track.model.TrackComment;
import com.oskarro.muzikum.track.model.TrackPageResponse;
import com.oskarro.muzikum.track.model.UrlSource;
import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.user.UserRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class TrackServiceImpl implements TrackService {

    @PersistenceContext
    EntityManager entityManager;

    private final TrackRepository trackRepository;
    private final UserRepository userRepository;
    private final TrackCommentRepository trackCommentRepository;
    private final PluginService pluginService;
    private final CoverRepository coverRepository;
    private final ImageRepository imageRepository;

    public TrackServiceImpl(TrackRepository trackRepository, UserRepository userRepository,
                            PluginService pluginService, TrackCommentRepository trackCommentRepository,
                            ImageRepository imageRepository, CoverRepository coverRepository) {
        super();
        this.trackRepository = trackRepository;
        this.userRepository = userRepository;
        this.trackCommentRepository = trackCommentRepository;
        this.pluginService = pluginService;
        this.imageRepository = imageRepository;
        this.coverRepository = coverRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Track> findAll() {
        return trackRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Track> findById(Integer id) {
        return trackRepository.findById(id);
    }

    @Override
    @Transactional
    public Track saveTrack(Track track) {
        if (!Objects.equals(track.getUrl(), "")) {
            try {
                if (Objects.equals(track.getUrlSource(), UrlSource.KRAKENFILES.toString())) {
                    String jsonUrl = pluginService.getJsonUrlFromWebsiteUrl(track.getUrl());
                    PluginKrakenResponse response = pluginService.readJsonFromKrakenFiles(jsonUrl);
                    String pluginScript = pluginService.prepareScriptForKrakenfiles(response);
                    track.setUrlPlugin(pluginScript);
                } else if (Objects.equals(track.getUrlSource(), UrlSource.ZIPPYSHARE.toString())) {
                    String pluginScript = pluginService.prepareScriptForZippyshare(track.getUrl());
                    track.setUrlPlugin(pluginScript);
                } else if (Objects.equals(track.getUrlSource(), UrlSource.SOUNDCLOUD.toString())) {
                    // TODO SOUNDCLOUD
                }
                Cover cover = coverRepository.findTopByUrl(track.getUrl());
                if (cover != null) {
                    track.setCover(cover);
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
        return trackRepository.save(track);
    }

    @Override
    public List<Track> findTracksByGenre(String genre) {
        return trackRepository.findTracksByGenre(genre.toUpperCase());
    }

    @Override
    public List<Track> findAllTracksFromPlaylist(Integer id) {
        return new ArrayList<>(trackRepository.findTracksByPlaylistId(id));
    }

    @Override
    public List<Track> findAllTracksFromVideo(Integer id) {
        return new ArrayList<>(trackRepository.findTracksByVideoId(id));
    }

    @Override
    public Track getRandomTrack() {
        long tracksQuantity = trackRepository.count();
        int index = (int) (Math.random() * tracksQuantity);
        Page<Track> trackPage = trackRepository.findAll(PageRequest.of(index, 1));
        Track track = null;
        if (trackPage.hasContent()) {
            track = trackPage.getContent().get(0);
        }
        return track;
    }

    @Override
    public Track getMostPopularTrackByGenre(String genre) {
        List<Track> allTracksByGenre = findTracksByGenre(genre);
        return allTracksByGenre
                .stream()
                .filter(track -> track.getPoints() != null)
                .max(Comparator.comparing(Track::getPoints))
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Track> getListOfMostPopularTracksByGenre(String genre, Integer numberOfTracks) {
        List<Track> allTracksByGenre = findTracksByGenre(genre);

        return allTracksByGenre.stream()
                .filter(track -> track.getPoints() != null)
                .sorted(Comparator.comparingInt(Track::getPoints).reversed())
                .limit(numberOfTracks)
                .collect(Collectors.toList());
    }

    @Override
    public List<Track> getLastAddedTracksByGenre(String genre, Integer numberOfTracks) {
        List<Track> fetchedTracks = trackRepository.findByGenreOrderByCreatedAtDesc(genre.toUpperCase());
        return fetchedTracks
                .stream()
                .limit(numberOfTracks)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Track> getLastAddedTracksByUsername(String username, Integer numberOfTracks) {
        List<Track> fetchedTracks = trackRepository.findByUserUsernameOrderByCreatedAtDesc(username);
        return fetchedTracks
                .stream()
                .limit(numberOfTracks)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Track> getAddedTracksByGenreFromPage(String genre, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Track> fetchedTracks =
                trackRepository.findByGenreAndUrlSourceIsNotNullOrderByCreatedAtDesc(genre.toUpperCase(), pageable);
        return fetchedTracks
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public TrackPageResponse getTrackPageByGenre(String genre, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Track> fetchedTracks =
                trackRepository.findByGenreAndUrlSourceIsNotNullOrderByCreatedAtDesc(genre.toUpperCase(), pageable);
        List<Track> trackList = fetchedTracks
                                    .stream()
                                    .filter(Objects::nonNull)
                                    .filter(t -> t.getUrl() != null)
                                    .collect(Collectors.toList());
        return TrackPageResponse.builder()
                .trackList(trackList)
                .totalPages(fetchedTracks.getTotalPages())
                .totalElements(fetchedTracks.getTotalElements())
                .numberPage(fetchedTracks.getNumber())
                .build();
    }

    @Override
    public TrackPageResponse getTrackPageByUserUsername(String username, int page) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<Track> fetchedTracks = trackRepository.findByUserUsernameOrderByCreatedAtDesc(username, pageable);
        List<Track> trackList = fetchedTracks
                                    .stream()
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.toList());
        return TrackPageResponse.builder()
                .trackList(trackList)
                .totalPages(fetchedTracks.getTotalPages())
                .totalElements(fetchedTracks.getTotalElements())
                .numberPage(fetchedTracks.getNumber())
                .build();
    }

    @Override
    public List<TrackComment> getAllTrackCommentsByTrackId(Integer trackId) {
        return trackCommentRepository.findTrackCommentsByTrackIdOrderByCreatedAtDesc(trackId);
    }

    @Override
    public TrackComment saveTrackComment(TrackComment trackComment) {
        return trackCommentRepository.save(trackComment);
    }

    @Override
    public void deleteTrackCommentById(Integer commentId) {
        trackCommentRepository.deleteTrackCommentById(commentId);
    }

    @Override
    public long getNumberOfTracksAddedByTheUser(String username) {
        return trackRepository.countTracksByUserUsername(username);
    }

}
