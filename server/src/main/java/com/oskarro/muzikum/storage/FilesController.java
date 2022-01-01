package com.oskarro.muzikum.storage;

import com.oskarro.muzikum.track.TrackRepository;
import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.user.AuthProvider;
import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.user.UserRepository;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    FilesStorageService filesStorageService;
    ImageRepository imageRepository;
    UserRepository userRepository;
    CoverRepository coverRepository;
    TrackRepository trackRepository;

    public FilesController(FilesStorageService filesStorageService, ImageRepository imageRepository,
                           UserRepository userRepository, CoverRepository coverRepository,
                           TrackRepository trackRepository, ServletContext servletContext) {
        this.filesStorageService = filesStorageService;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.coverRepository = coverRepository;
        this.trackRepository = trackRepository;
        this.servletContext = servletContext;
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

    @GetMapping(value = "/cover/{trackId}")
    @ResponseBody
    @Transactional
    public ResponseEntity<Resource> getCoverImage(@PathVariable Integer trackId) {
        Optional<Track> track = trackRepository.findById(trackId);
        if (track.isPresent()) {
            Cover cover = coverRepository.findById(track.get().getCover().getId()).orElse(null);
            if (cover != null) {
                Resource file = filesStorageService.loadCover(cover.getName(), cover.getId());
                return Optional
                        .ofNullable(file)
                        .map(x -> ResponseEntity.ok().body(x))
                        .orElseGet(() -> ResponseEntity.notFound().build());
            } else {
                log.info("Can't find cover for track with id: ");
                return null;
            }
        } else {
            log.info("Can't find cover for track with id: ");
            return null;
        }
    }

    @GetMapping(value = "/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = filesStorageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "getFile", path.getFileName().toString())
                    .build().toString();
            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping(value = "/avatar/{username}")
    @ResponseBody
    @Transactional
    public ResponseEntity<Resource> getImage(@PathVariable String username) {
        Image image = imageRepository.findByUserUsername(username)
                .orElse(null);
        if (image != null) {
            Resource file = filesStorageService.load(image.getName(), username);
            return Optional
                    .ofNullable(file)
                    .map(x -> ResponseEntity.ok().body(x))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            log.info("Can't find image for user with username: {}", username);
            return null;
        }
    }
}
