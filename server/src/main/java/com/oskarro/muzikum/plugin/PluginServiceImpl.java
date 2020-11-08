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
public class PluginServiceImpl implements PluginService {

    @Override
    public PluginKrakenResponse readJsonFromKrakenFiles(String jsonUrl) throws IOException, ParseException {
        Object object = new JSONParser().parse(IOUtils.toString(new URL(jsonUrl), UTF_8));
        JSONObject jsonObject =  (JSONObject) object;
        return PluginKrakenResponse.builder()
                .title(String.valueOf(jsonObject.get("title")))
                .numberOfDownloads(String.valueOf(jsonObject.get("downloads")))
                .size(String.valueOf(jsonObject.get("size")))
                .views(String.valueOf(jsonObject.get("views")))
                .bitrate(String.valueOf(jsonObject.get("bitrate")))
                .uploadDate(String.valueOf(jsonObject.get("uploadDate")))
                .hash(String.valueOf(jsonObject.get("hash")))
                .build();
    }

    @Override
    public String prepareScript(PluginKrakenResponse response) {
        StringBuilder script = new StringBuilder("https://krakenfiles.com/getEmbedPlayer/");
        script.append(response.getHash());
        script.append("?width=550&autoplay=false&date=");
        script.append(response.getUploadDate());
        System.out.println(script);
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
                "width=550";
    }

//          <iframe src="https://api.zippyshare.com/api/jplayer_embed.jsp?key=XGBt2z5D&amp;server=www87&amp;width=550"
//          width="550" height="92" frameborder="0"></iframe>

    // https://www35.zippyshare.com/v/kvVGY0Yt/file.html

//          <embed flashvars="baseurl=https://api.zippyshare.com/api/&amp;file=vJgPJz71&amp;server=26&amp;
//          flashid=zs_playervJgPJz71&amp;availablequality=both&amp;bordercolor=#cccccc&amp;forecolor=#000000&amp;
//          backcolor=#e8e8e8&amp;darkcolor=#000000&amp;lightcolor=#ff6600"
//
//          allowfullscreen="false" allowscriptaccess="always" type="application/x-shockwave-flash"
//          src="https://api.zippyshare.com/api/player.swf"
//          name="zs_playervJgPJz71" wmode="transparent" id="zs_playervJgPJz71" width="450px" height="80">

}
