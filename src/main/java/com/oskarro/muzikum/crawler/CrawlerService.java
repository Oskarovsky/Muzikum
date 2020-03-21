package com.oskarro.muzikum.crawler;

import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLSpanElement;
import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.track.TrackService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
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

    public String getWeb(Provider provider, String genre) {
        try {
            final WebClient webClient = new WebClient();
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            final HtmlPage page = webClient.getPage(provider.getUrl());

            HtmlButton select = page.getFirstByXPath( "//div[@class='btn-group category']//button");
            select.click();

            HtmlListItem htmlElement;
            switch (genre) {
                case "disco":
                    htmlElement = page.getFirstByXPath( "//div[@class='btn-group category open']//ul//li[3]");
                    break;
                case "club":
                    htmlElement = page.getFirstByXPath( "//div[@class='btn-group category open']//ul//li[4]");
                    break;
                case "set":
                    htmlElement = page.getFirstByXPath( "//div[@class='btn-group category open']//ul//li[5]");
                    break;
                case "other":
                    htmlElement = page.getFirstByXPath( "//div[@class='btn-group category open']//ul//li[6]");
                    break;
                default:
                    htmlElement = page.getFirstByXPath( "//div[@class='btn-group category open']//ul//li[0]");
            }

            htmlElement.setAttribute("class", "active");
            System.out.println(htmlElement.getAttribute("data-category"));
            htmlElement.click();

            webClient.waitForBackgroundJavaScript(3 * 1000);

            final List<HtmlElement> spanElements = page.getByXPath("//span[@class='news-title']//a");

            for (Object obj : spanElements) {
                HtmlAnchor anchor = (HtmlAnchor) obj;
                String name = anchor.getTextContent().trim();
                Track track = Track.builder()
                        .artist(name.split(" - ")[0])
                        .title(name.split(" - ")[1])
                        .provider(provider)
                        .url(anchor.getHrefAttribute().trim())
                        .build();
                if (name.contains("(")) {
                    track.setVersion(name.substring(name.indexOf("(")+1, name.indexOf(")")));
                } else {
                    track.setVersion("Original Mix");
                }
                trackService.saveTrack(track);
            }
            return String.format("Entire %s tracklist has been fetched from nuteczki.eu", genre);
        } catch (IOException e) {
            e.printStackTrace();
            return "Tracks from nuteczki.eu cannot be fetched";
        }
    }

    public String parseWeb(Provider provider) {
        try {
            Document document =  Jsoup.connect(provider.getUrl()).get();
            Elements views = document.getElementsByClass("view");
            for (Element view : views) {
                Element element = view.getElementsByTag("a").first();
                Element element1 = element.getElementsByTag("img").first();
                System.out.println(element1.attr("alt"));
                System.out.println(element.attr("href"));
            }
            return "XXXXX";
        } catch (IOException e) {
            log.error(String.format("There are a problem with parsing website: %s", provider.getName()));
            e.printStackTrace();
            return null;
        }
    }

}
