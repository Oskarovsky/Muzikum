package com.oskarro.muzikum.crawler;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.track.Genre;
import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.track.TrackService;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONValue;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class CrawlerService {

    TrackService trackService;

    public CrawlerService(TrackService trackService) {
        this.trackService = trackService;
    }

    public String getWeb(Provider provider, Genre genre) {
        return null;
    }

    public String parseWeb(Provider provider) {
        String urlSite = "top40dancedjs";
        try {
            final WebClient webClient = new WebClient();
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            HtmlPage page = webClient.getPage(provider.getUrl());

            webClient.waitForBackgroundJavaScript(3 * 1000);
            page = webClient.getPage(provider.getUrl());

            final List<HtmlElement> divElements = page.getByXPath("//div[@class='content']");
            for (HtmlElement element : divElements) {
                System.out.println(element.asText());
            }


/*            for (Element element : formsList) {
                Track track = Track.builder()
                        .title(element.getElementsByTag("h2").text())
                        .artist(element.getElementsByTag("h1").text())
                        .provider(provider)
                        .build();
                trackService.saveTrack(track);
            }*/
            return "All tracklist has been fetched from ILoveMusic.de";
        } catch (IOException e) {
            log.error(String.format("There are a problem with parsing website: %s", provider.getName()));
            e.printStackTrace();
            return null;
        }
    }
}
