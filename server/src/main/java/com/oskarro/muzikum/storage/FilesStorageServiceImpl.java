package com.oskarro.muzikum.storage;

import com.oskarro.muzikum.track.TrackRepository;
import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    UserRepository userRepository;
    ImageRepository imageRepository;
    TrackRepository trackRepository;
    CoverRepository coverRepository;

    public FilesStorageServiceImpl(UserRepository userRepository, ImageRepository imageRepository,
                                   TrackRepository trackRepository, CoverRepository coverRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.trackRepository = trackRepository;
        this.coverRepository = coverRepository;
    }

    private final Path rootPath = Paths.get("uploads");
    private final Path userRootPath = Paths.get("uploads/user");
    private final Path coverRootPath = Paths.get("uploads/cover");

    @Override
    public void init() {
        try {
            Files.createDirectory(rootPath);
            Files.createDirectory(userRootPath);
            Files.createDirectory(coverRootPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Transactional
    @Override
    public void save(MultipartFile file, String username, String destination) {
        try {
            final Path userPath = Paths.get(userRootPath.toString() + "/" + username);
            FileSystemUtils.deleteRecursively(userPath.toFile());
            Files.createDirectory(Paths.get(userRootPath.toString() + "/" + username));
            Files.copy(file.getInputStream(), userPath.resolve(Objects.requireNonNull(file.getOriginalFilename())));

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email:" + username));

            if (imageRepository.existsByUserId(user.getId())) {
                imageRepository.deleteByUserId(user.getId());
            }

            Image image = Image.builder()
                    .name(file.getOriginalFilename())
                    .user(user)
                    .destination(destination)
                    .type(file.getContentType())
                    .pic(file.getBytes())
                    .build();
            imageRepository.save(image);
            System.out.println("Image saved");

        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public void saveCover(MultipartFile file, String username, String trackUrl) {
        try {
            final Path coverPath = Paths.get(coverRootPath.toString());
            Files.copy(file.getInputStream(), coverPath.resolve(Objects.requireNonNull(file.getOriginalFilename())));

            Cover cover = Cover.builder()
                    .name(file.getOriginalFilename())
                    .url(trackUrl)
                    .type(file.getContentType())
                    .pic(file.getBytes())
                    .build();
            coverRepository.save(cover);
            System.out.println("Cover saved for track from url: " + trackUrl);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename, String username) {
        try {
            final Path userPath = Paths.get(userRootPath.toString() + "/" + username);
            Path file = userPath.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public Resource loadCover(String filename, Integer coverId) {
        try {
            final Path coverPath = Paths.get(coverRootPath.toString());
            Path file = coverPath.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootPath, 1).filter(path -> !path.equals(this.rootPath)).map(this.rootPath::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
