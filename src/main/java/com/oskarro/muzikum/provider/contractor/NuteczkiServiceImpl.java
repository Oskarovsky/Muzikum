package com.oskarro.muzikum.provider.contractor;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.oskarro.muzikum.crawler.CrawlerService;
import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.track.TrackService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class NuteczkiServiceImpl implements NuteczkiService {

    // TODO Enum for choosing music genre

    CrawlerService crawlerService;
    TrackService trackService;

    public NuteczkiServiceImpl(CrawlerService crawlerService, TrackService trackService) {
        this.crawlerService = crawlerService;
        this.trackService = trackService;
    }

    @Override
    public void getDocument(Provider provider) {
        crawlerService.parseWeb(provider);
    }

    @Override
    public String getTrackList(Provider provider) {
        try {
            Document document =  Jsoup.connect(provider.getUrl()).get();
            Elements views = document.getElementsByClass("view");
            for (Element view : views) {
                Element element = view.getElementsByTag("a").first();
                Element element1 = element.getElementsByTag("img").first();
                System.out.println(element1.attr("alt"));
                System.out.println(element.attr("href"));
                Track track = Track.builder()
                        .title(element1.attr("alt"))
                        .artist(element.attr("href"))
                        .build();
                trackService.saveTrack(track);
            }
            return "All tracklist has been fetched from nuteczki.eu";
        } catch (IOException e) {
            log.error(String.format("There are a problem with parsing website: %s", provider.getName()));
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getTrackListByGenre(Provider provider) {
        try {
            final WebClient webClient = new WebClient();
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            final HtmlPage page = webClient.getPage(provider.getUrl());

            HtmlButton select = page.getFirstByXPath( "//div[@class='btn-group category']//button");
            select.click();

            HtmlListItem htmlElement = page.getFirstByXPath( "//div[@class='btn-group category open']//ul//li[4]");
            htmlElement.setAttribute("class", "active");
            System.out.println(htmlElement.getAttribute("data-category"));
            htmlElement.click();

            webClient.waitForBackgroundJavaScript(7 * 1000);

            final List<HtmlElement> spanElements = page.getByXPath("//span[@class='news-title']//a");

            for (Object obj : spanElements) {
                HtmlAnchor anchor = (HtmlAnchor) obj;
                System.out.println(anchor.getTextContent().trim());
                System.out.println(anchor.getHrefAttribute());
                Track track = Track.builder()
                        .title(anchor.getTextContent().trim())
                        .url(anchor.getHrefAttribute().trim())
                        .build();
                trackService.saveTrack(track);
            }
            return "All tracklist has been fetched from nuteczki.eu";
        } catch (IOException e) {
            e.printStackTrace();
            return "errrrrrror!";
        }
    }
}
