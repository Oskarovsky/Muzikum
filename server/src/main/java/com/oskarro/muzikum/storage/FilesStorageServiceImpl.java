package com.oskarro.muzikum.storage;

import com.oskarro.muzikum.track.TrackRepository;
import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
public class FilesStorageServiceImpl implements FilesStorageService {

    UserRepository userRepository;
    ImageRepository imageRepository;
    TrackRepository trackRepository;
    CoverRepository coverRepository;
    ArticleImageRepository articleImageRepository;

    public FilesStorageServiceImpl(UserRepository userRepository,
                                   ImageRepository imageRepository,
                                   TrackRepository trackRepository,
                                   CoverRepository coverRepository,
                                   ArticleImageRepository articleImageRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.trackRepository = trackRepository;
        this.coverRepository = coverRepository;
        this.articleImageRepository = articleImageRepository;
    }


    private final Path rootPath = Paths.get("/mnt/backend/uploads");
    private final Path userRootPath = Paths.get("/mnt/backend/uploads/user");
    private final Path coverRootPath = Paths.get("/mnt/backend/uploads/cover");
    private final Path articleRootPath = Paths.get("/mnt/backend/uploads/article");

    @Override
    public void init() {
        try {
            log.info("INIT DIRECTORIES");
            Files.createDirectory(rootPath);
            Files.createDirectory(userRootPath);
            Files.createDirectory(coverRootPath);
            Files.createDirectory(articleRootPath);
        } catch (IOException e) {
            log.info("Could not initialize folder for upload!", e);
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Transactional
    @Override
    public void save(final MultipartFile file, final String username, final String destination) {
        try {
            String targetFilename = saveImageFile(file, FileResourceType.USER_IMAGE, username);
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email:" + username));

            if (imageRepository.existsByUserId(user.getId())) {
                imageRepository.deleteByUserId(user.getId());
            }

            Image image = Image.builder()
                    .name(targetFilename)
                    .user(user)
                    .destination(destination)
                    .type(file.getContentType())
                    .pic(file.getBytes())
                    .build();
            imageRepository.save(image);
            System.out.println("Image saved");
        } catch (Exception e) {
            log.info("Could not store the file. Error: ", e);
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public void saveCover(final MultipartFile file, final String username, final String trackUrl) {
        try {
            String targetFilename = saveImageFile(file, FileResourceType.COVER_IMAGE, trackUrl);
            Cover cover = Cover.builder()
                    .name(targetFilename)
                    .url(trackUrl)
                    .type(file.getContentType())
                    .pic(file.getBytes())
                    .build();
            coverRepository.save(cover);
            System.out.println("Cover saved for track from url: " + trackUrl);
        } catch (Exception e) {
            log.info("Could not store the file.", e);
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public void saveArticleImage(final MultipartFile file, final String username, final Integer articleId) {
        try {
            String targetFilename = saveImageFile(file, FileResourceType.ARTICLE_IMAGE, articleId);
            ofNullable(articleImageRepository.findByArticleId(articleId))
                    .ifPresent(s -> {
                        log.info(String.format("Image already exists for post with id %s", articleId));
                        throw new RuntimeException(String.format("Image already exists for post with id %s", articleId));
                    });

            ArticleImage image = ArticleImage.builder()
                    .name(targetFilename)
                    .type(file.getContentType())
                    .pic(file.getBytes())
                    .articleId(articleId)
                    .build();
            articleImageRepository.save(image);
            System.out.println("Article image saved to database");
        } catch (IOException e) {
            log.info("Could not save article image to database", e);
            e.printStackTrace();
        }
    }

    public String saveImageFile(final MultipartFile file, final FileResourceType type, final Object discriminator) {
        Path directoryPath;
        String targetFilename;
        String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
        try {
            switch (type) {
                case ARTICLE_IMAGE -> {
                    File fileArticleImage = new File("/mnt/backend/uploads/article");
                    targetFilename = String.format("articleImage_%s%s", discriminator, suffix);
                    Files.copy(file.getInputStream(), Path.of(fileArticleImage.toPath() + "/" + targetFilename));
                }
                case COVER_IMAGE -> {
                    File fileCoverImage = new File("/mnt/backend/uploads/cover");
                    targetFilename = String.format("coverImage_%s%s", new Random().nextInt(999999999), suffix);
                    Files.copy(file.getInputStream(), Path.of(fileCoverImage.toPath() + "/" + targetFilename));
                }
                case USER_IMAGE -> {
                    File fileUserImage = new File("/mnt/backend/uploads/user/" + discriminator);
                    FileSystemUtils.deleteRecursively(fileUserImage);
                    Files.createDirectory(Paths.get("/mnt/backend/uploads/user/" + discriminator));
                    targetFilename = String.format("userImage_%s%s", discriminator, suffix);
                    Files.copy(file.getInputStream(), Path.of(fileUserImage.toPath() + "/" + targetFilename));
                }
                default -> {
                    directoryPath = Paths.get(rootPath.toString());
                    targetFilename = String.format("image_%s%s", discriminator, suffix);
                    Files.copy(file.getInputStream(), directoryPath.resolve(targetFilename));
                }
            }
            return targetFilename;
        } catch (IOException e) {
            e.printStackTrace();
            log.info("Could not save file!", e);
            throw new RuntimeException("Could not save file!");
        }
    }

    @Override
    public Resource loadImage(final String filename, final Object discriminator, final FileResourceType type) {
        try {
            String directoryPath;
            switch (type) {
                case ARTICLE_IMAGE -> directoryPath = articleRootPath.toString();
                case COVER_IMAGE -> directoryPath = coverRootPath.toString();
                case USER_IMAGE -> directoryPath = userRootPath + "/" + discriminator;
                default -> directoryPath = rootPath.toString();
            }
            File fileImage = new File(directoryPath + "/" + filename);
            Resource resource = new UrlResource(fileImage.toURI());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.info("Could not read file!");
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            log.info("Could not read the file!", e);
            throw new RuntimeException("Could not read the file!");
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootPath, 1)
                    .filter(path -> !path.equals(this.rootPath))
                    .map(this.rootPath::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
