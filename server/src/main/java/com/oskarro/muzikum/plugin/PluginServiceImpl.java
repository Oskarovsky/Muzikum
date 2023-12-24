package com.oskarro.muzikum.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@Slf4j
public class PluginServiceImpl implements PluginService {

    @Override
    public PluginKrakenResponse readJsonFromKrakenFiles(String jsonUrl) throws IOException, ParseException {
        Object object = new JSONParser().parse(IOUtils.toString(new URL(jsonUrl), UTF_8));
        JSONObject jsonObject = (JSONObject) object;
        return PluginKrakenResponse.builder()
                .title(String.valueOf(jsonObject.get("title")))
                .numberOfDownloads(String.valueOf(jsonObject.get("downloads")))
                .size(String.valueOf(jsonObject.get("size")))
                .views(String.valueOf(jsonObject.get("views")))
                .bitrate(String.valueOf(jsonObject.get("bitrate")))
                .uploadDate(String.valueOf(jsonObject.get("uploadDate")))
                .hash(String.valueOf(jsonObject.get("hash")))
                .server(String.valueOf(jsonObject.get("server")))
                .build();
    }

    @Override
    public String prepareScriptForKrakenfiles(PluginKrakenResponse response) {
        StringBuilder script = new StringBuilder("https://");
        script.append(response.getServer());
        script.append("krakenfiles.com/getEmbedPlayer/");
        script.append(response.getHash());
        script.append("?width=1000&autoplay=false&date=");
        script.append(response.getUploadDate());
        log.info(script.toString());
        return String.valueOf(script);
    }

    @Override
    public String getJsonUrlFromWebsiteUrl(String websiteUrl) {
        if (websiteUrl.contains("view")) {
            String trackHash = websiteUrl.substring(websiteUrl.indexOf("view/") + 5, websiteUrl.indexOf("/file"));
            return "https://krakenfiles.com/json/" + trackHash;
        } else {
            return websiteUrl;
        }
    }

    @Override
    public String prepareScriptForZippyshare(String websiteUrl) {
        String serverId = "www" + websiteUrl.substring(websiteUrl.indexOf("www") + 3, websiteUrl.indexOf(".zippy"));
        String trackHash = websiteUrl.substring(websiteUrl.indexOf("/v/") + 3, websiteUrl.indexOf("/file"));
        return "https://api.zippyshare.com/api/jplayer_embed.jsp?" +
                "key=" + trackHash + "&" +
                "server=" + serverId + "&" +
                "autostart=true&" +
                "width=1000";
    }

}
