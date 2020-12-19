package com.oskarro.muzikum.service;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.video.Video;
import com.oskarro.muzikum.video.VideoRepository;
import com.oskarro.muzikum.video.VideoService;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static java.nio.charset.StandardCharsets.UTF_8;

@Import(com.oskarro.muzikum.config.TestConfiguration.class)
@RunWith(SpringRunner.class)
@SpringBootTest
//@Ignore
public class VideoServiceTest {

    @Autowired
    VideoService videoService;

    @Autowired
    VideoRepository videoRepository;

    @Test
    public void test_updateYoutubeDetailsOneVideo() throws IOException, ParseException {
        String videoUrl = "moFuKK_Ac";
        String basicUrl = "https://www.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics&id=WRooj5n80uo&key=AIzaSyDeSTKXmaye9ula3yzeA4BJWO1vA8_i8kE";
        Object object = new JSONParser().parse(IOUtils.toString(new URL(basicUrl), UTF_8));
        JSONObject jsonObject = (JSONObject) object;
        JSONArray val1 = (JSONArray) jsonObject.get("items");

        JSONObject resultObject = (JSONObject) val1.get(0);
        String val2 = String.valueOf(resultObject.get("statistics"));

        JSONObject resultObject2 = (JSONObject) resultObject.get("statistics");
        String val3 = String.valueOf(resultObject2.get("viewCount"));

        System.out.println(val3);
        Video video = videoRepository.findByUrl(videoUrl)
                .orElseThrow(() -> new ResourceNotFoundException("Video", "videoUrl", videoUrl));
        video.setViewCount(Integer.valueOf(val3));
    }
}
