package com.oskarro.muzikum.plugin;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class PluginServiceImpl {

    public PluginKrakenResponse readJsonFromKrakenFiles(String jsonUrl) throws IOException, ParseException {
        Object object = new JSONParser().parse(IOUtils.toString(new URL(jsonUrl), UTF_8));
        JSONObject jsonObject =  (JSONObject) object;
        return PluginKrakenResponse.builder()
                .title(String.valueOf(jsonObject.get("title")))
                .uploadDate(String.valueOf(jsonObject.get("uploadDate")))
                .hash(String.valueOf(jsonObject.get("hash")))
                .build();
    }

    public String prepareScript(PluginKrakenResponse response) {
        StringBuilder script = new StringBuilder("https://krakenfiles.com/getEmbedPlayer/");
        script.append(response.getHash());
        script.append("?width=450&amp;autoplay=false&amp;date=");
        script.append(response.getUploadDate());
        System.out.println(script);
        return String.valueOf(script);
    }

}
