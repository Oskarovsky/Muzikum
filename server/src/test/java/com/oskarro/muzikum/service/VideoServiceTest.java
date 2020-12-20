package com.oskarro.muzikum.service;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.security.jwt.JwtAuthenticationFilter;
import com.oskarro.muzikum.video.Video;
import com.oskarro.muzikum.video.VideoRepository;
import com.oskarro.muzikum.video.VideoService;
import groovy.util.logging.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URL;

import static java.nio.charset.StandardCharsets.UTF_8;

@Import(com.oskarro.muzikum.config.TestConfiguration.class)
@RunWith(SpringRunner.class)
@SpringBootTest
//@Ignore
public class VideoServiceTest {

    @Value("${google.api.key}")
    private String GOOGLE_API_KEY;

    @Autowired
    VideoService videoService;

    @Autowired
    VideoRepository videoRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoServiceTest.class);


    @Test
    public void test_updateYoutubeDetailsOneVideo() throws IOException, ParseException {
        String videoUrl = "moFuKK_Ac";
        String basicUrl = "https://www.googleapis.com/youtube/v3/videos" +
                "?part=statistics" +
                "&id=" + videoUrl +
                "&key=" + GOOGLE_API_KEY;

        Object object = new JSONParser().parse(IOUtils.toString(new URL(basicUrl), UTF_8));
        JSONObject jsonObject = (JSONObject) object;
        JSONArray val1 = (JSONArray) jsonObject.get("items");

        if (!val1.isEmpty()) {
            JSONObject resultObject = (JSONObject) val1.get(0);
            JSONObject resultObject2 = (JSONObject) resultObject.get("statistics");
            String val3 = String.valueOf(resultObject2.get("viewCount"));
            System.out.println(val3);
            Video video = videoRepository.findByUrl(videoUrl)
                    .orElseThrow(() -> new ResourceNotFoundException("Video", "videoUrl", videoUrl));
            video.setViewCount(Integer.valueOf(val3));
        } else {
            LOGGER.warn("Video statistics with url {} cannot be updated", videoUrl);
        }

    }

    @Test
    public void test_updateYoutubeDetailsInfo() throws IOException, ParseException {
        videoService.updateYoutubeInformation("WRooj5n80uo");
    }

    @Test
    public void test_getTop10PopularVideo() {
        System.out.println(videoService.getTop10PopularVideos());
    }


}
