package com.oskarro.muzikum.storage;

import com.oskarro.muzikum.track.TrackRepository;
import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.user.AuthProvider;
import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.user.UserRepository;
import javassist.NotFoundException;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/api/storage")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class FilesController {

    @Autowired
    ServletContext servletContext;

    FilesStorageService filesStorageService;
    ImageRepository imageRepository;
    UserRepository userRepository;
    CoverRepository coverRepository;
    TrackRepository trackRepository;

    public FilesController(FilesStorageService filesStorageService, ImageRepository imageRepository,
                           UserRepository userRepository, CoverRepository coverRepository,
                           TrackRepository trackRepository) {
        this.filesStorageService = filesStorageService;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.coverRepository = coverRepository;
        this.trackRepository = trackRepository;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file,
                                                      @RequestParam("username") String username,
                                                      @RequestParam("destination") String destination) {
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

    @PostMapping(value = "/uploadCover")
    @Transactional
    public ResponseEntity<ResponseMessage> uploadFileCover(@RequestParam("file") MultipartFile file,
                                                           @RequestParam("username") String username,
                                                           @RequestParam("trackUrl") String trackUrl,
                                                           @RequestParam("destination") String destination) {
        try {
            filesStorageService.save(file, username, trackUrl, destination);
            String message = "Dodano zdjęcie jako okładkę do utworu: " + file.getOriginalFilename() + ". Odśwież stronę.";
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

    @PostMapping(value = "/uploadFileCoverTrack")
    public ResponseEntity<ResponseMessage> uploadFileCoverTrack(@RequestParam("file") MultipartFile file,
                                                      @RequestParam("username") String username,
                                                      @RequestParam("trackUrl") String trackUrl) {
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

    @GetMapping(value = "/getCoverId")
    @Transactional
    @ResponseBody
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Integer getCoverId(@RequestParam("filename") String fileName) {
        Image image = filesStorageService.findImageByFileName(fileName);
        String trackUrl = image.getUrl();
        // TODO get image by track zippy/krakenfiles
        // TODO get image for track if there are the same link url
        return image.getId();
    }

    @GetMapping(value = "/getTrackCover/{trackId}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Cover> getTrackCover(@PathVariable Integer trackId) {
        Cover cover = filesStorageService.getTrackCover(trackId);
        return ResponseEntity.status(HttpStatus.OK).body(cover);
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

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = filesStorageService.load(filename, "default");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping(value = "/{username}/avatar")
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

    @GetMapping(value = "/{trackId}/cover")
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



    @GetMapping(value = "/{userId}/imageUrl")
    @Transactional
    public ResponseEntity<String> getImageUrl(@PathVariable Integer userId) throws NotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Can't find user with id: " + userId));
        if (!user.getProvider().equals(AuthProvider.local)) {
            return ResponseEntity.ok().body(user.getImageUrl());
        }
        return null;
    }

}
