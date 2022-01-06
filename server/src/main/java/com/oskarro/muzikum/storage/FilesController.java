package com.oskarro.muzikum.storage;

import com.oskarro.muzikum.article.post.Post;
import com.oskarro.muzikum.article.post.PostRepository;
import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.track.TrackRepository;
import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.user.UserRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Controller
@RequestMapping(value = "/api/storage")
@CrossOrigin
@Slf4j
public class FilesController {

    /**
     * Servlet defines a set of methods that a servlet uses to communicate with its servlet container
     * (for example, to get the MIME type of file, dispatch requests, or write to a log file)
     * */
    final ServletContext servletContext;

    final FilesStorageService filesStorageService;
    final ImageRepository imageRepository;
    final UserRepository userRepository;
    final CoverRepository coverRepository;
    final TrackRepository trackRepository;
    final PostRepository postRepository;
    final ArticleImageRepository articleImageRepository;

    public FilesController(final FilesStorageService filesStorageService,
                           final ImageRepository imageRepository,
                           final UserRepository userRepository,
                           final CoverRepository coverRepository,
                           final TrackRepository trackRepository,
                           final ServletContext servletContext,
                           final PostRepository postRepository,
                           final ArticleImageRepository articleImageRepository) {
        this.filesStorageService = filesStorageService;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.coverRepository = coverRepository;
        this.trackRepository = trackRepository;
        this.servletContext = servletContext;
        this.postRepository = postRepository;
        this.articleImageRepository = articleImageRepository;
    }

    @PostMapping(value = "/uploadFile", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ResponseMessage> uploadFileNew(@RequestPart("file") @Valid @NonNull @NotBlank MultipartFile file,
                                                         @RequestPart("username") @NotBlank String username,
                                                         @RequestPart("destination") @NotBlank String destination) {
        try {
            filesStorageService.save(file, username, destination);
            String message = "Dodano zdjęcie: " + file.getOriginalFilename() + ". Odśwież stronę.";
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseMessage(message));
        } catch (Exception e) {
            String message = "Nie można załadować pliku: " + file.getOriginalFilename() + "!";
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseMessage(message));
        }
    }


    @PostMapping(value = "/uploadFileCoverTrack", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ResponseMessage> uploadFileCoverTrack(@RequestPart("file") @Valid @NonNull @NotBlank MultipartFile file,
                                                                @RequestPart("username") @NotBlank String username,
                                                                @RequestPart("trackUrl") @NotBlank String trackUrl) {
        try {
            filesStorageService.saveCover(file, username, trackUrl);
            String message = "Dodano zdjęcie: " + file.getOriginalFilename() + ". Odśwież stronę.";
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseMessage(message));
        } catch (Exception e) {
            String message = "Nie można załadować pliku: " + file.getOriginalFilename() + "!";
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseMessage(message));
        }
    }


    @PostMapping(value = "/uploadArticleImage", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ResponseMessage> uploadArticleImage(@RequestPart("file") @Valid @NonNull @NotBlank MultipartFile file,
                                                              @RequestPart("username") @NotBlank String username,
                                                              @RequestPart("articleId") @NotBlank String articleId) {
        try {
            filesStorageService.saveArticleImage(file, username, Integer.valueOf(articleId));
            String message = String.format("Dodano zdjęcie do artykułu: %s. Odśwież stronę.", file.getOriginalFilename());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseMessage(message));
        } catch (Exception e) {
            String message = String.format("Nie można załadować pliku: %s.", file.getOriginalFilename());
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseMessage(message));
        }
    }

    @GetMapping(value = "/avatar/{username}")
    @ResponseBody
    @Transactional
    public ResponseEntity<Resource> getImage(@PathVariable String username) {
        Image image = imageRepository.findByUserUsername(username).orElse(null);
        if (image != null) {
            Resource file = filesStorageService
                    .loadImage(image.getName(), username, FileResourceType.USER_IMAGE);
            return ofNullable(file)
                    .map(x -> ResponseEntity.ok().body(x))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            log.info("Can't find image for user with username: {}", username);
            throw new ResourceNotFoundException("Image", "username", username);
        }
    }

    @GetMapping(value = "/cover/{trackId}")
    @ResponseBody
    @Transactional
    public ResponseEntity<Resource> getCoverImage(@PathVariable Integer trackId) {
        Optional<Track> track = trackRepository.findById(trackId);
        if (track.isPresent()) {
            Cover cover = coverRepository.findById(track.get().getCover().getId()).orElse(null);
            if (cover != null) {
                Resource file = filesStorageService
                        .loadImage(cover.getName(), cover.getId(), FileResourceType.COVER_IMAGE);
                return ofNullable(file)
                        .map(x -> ResponseEntity.ok().body(x))
                        .orElseGet(() -> ResponseEntity.notFound().build());
            } else {
                log.info("Can't find cover for track with id: {}", trackId);
                throw new ResourceNotFoundException("Cover", "trackId", trackId);
            }
        } else {
            log.info("Can't find track with id: {}", trackId);
            throw new ResourceNotFoundException("Cover", "trackId", trackId);
        }
    }

    @GetMapping(value = "/article/{articleId}")
    @ResponseBody
    @Transactional
    public ResponseEntity<Resource> getArticleImage(@PathVariable Integer articleId) {
        Optional<Post> post = postRepository.findById(articleId);
        if (post.isPresent()) {
            ArticleImage articleImage = articleImageRepository.findByArticleId(articleId);
            if (articleImage != null) {
                Resource file = filesStorageService
                        .loadImage(articleImage.getName(), articleImage.getId(), FileResourceType.ARTICLE_IMAGE);
                return ofNullable(file)
                        .map(x -> ResponseEntity.ok().body(x))
                        .orElseGet(() -> ResponseEntity.notFound().build());
            } else {
                log.info("Can't find image for article with id: {}", articleId);
                throw new ResourceNotFoundException("Article Image", "articleId", articleId);

            }
        } else {
            throw new ResourceNotFoundException("Post", "id", articleId);
        }
    }

    @GetMapping(value = "/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = filesStorageService.loadAll()
                .map(path -> {
                    String filename = path.getFileName().toString();
                    String url = MvcUriComponentsBuilder
                            .fromMethodName(FilesController.class, "getFile", path.getFileName().toString())
                            .build().toString();
                    return new FileInfo(filename, url);
                })
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }
}
