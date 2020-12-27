package com.oskarro.muzikum.service;

import com.oskarro.muzikum.storage.FilesStorageService;
import com.oskarro.muzikum.storage.Image;
import com.oskarro.muzikum.storage.ImageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Import(com.oskarro.muzikum.config.TestConfiguration.class)
@RunWith(SpringRunner.class)
@SpringBootTest
//@Ignore
public class ImageServiceTest {

    @Autowired
    FilesStorageService filesStorageService;

    @Autowired
    ImageRepository imageRepository;


    @Test
    @Transactional
    public void test_getImageByFileName() {
        Image image = imageRepository.findByName("trackCover_floyd.png");
        System.out.println(image);
    }
}
