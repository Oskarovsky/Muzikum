package com.oskarro.muzikum.service;

import com.oskarro.muzikum.plugin.PluginKrakenResponse;
import com.oskarro.muzikum.plugin.PluginService;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@Import(com.oskarro.muzikum.config.TestConfiguration.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class PluginServiceTest {

    @Autowired
    PluginService pluginService;

    @Test
    public void test_getJsonUrlFromWebsiteUrl() {
        String url = pluginService.getJsonUrlFromWebsiteUrl("https://krakenfiles.com/view/9b77ee2da1/file.html");
        Assert.assertEquals(url, "https://krakenfiles.com/json/9b77ee2da1");
    }

    @Test
    public void test_readJsonFromKrakenFiles() throws IOException, ParseException {
        String url = "https://krakenfiles.com/json/9b77ee2da1";
        PluginKrakenResponse response =
                pluginService.readJsonFromKrakenFiles("https://krakenfiles.com/json/9b77ee2da1");
        Assert.assertEquals(response.getTitle(), "Oskarro - ELEKTROWNIA.mp3");
        Assert.assertEquals(response.getSize(), "100.45 MB");
        Assert.assertEquals(response.getHash(), "9b77ee2da1");
        Assert.assertEquals(response.getUploadDate(), "02-08-2020");
    }

    @Test
    public void test_prepareScript() throws IOException, ParseException {
        PluginKrakenResponse response =
                pluginService.readJsonFromKrakenFiles("https://krakenfiles.com/json/9b77ee2da1");
        String resultUrl = pluginService.prepareScriptForKrakenfiles(response);
        Assert.assertEquals(resultUrl,
                "https://krakenfiles.com/getEmbedPlayer/9b77ee2da1?width=550&autoplay=false&date=02-08-2020");
    }

    @Test
    public void test_prepareScriptForZippyshare() {
        String scriptResult = pluginService.prepareScriptForZippyshare("https://www35.zippyshare.com/v/kvVGY0Yt/file.html");
        System.out.println(scriptResult);
    }

}
