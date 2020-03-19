package com.oskarro.muzikum.crawler;

import com.oskarro.muzikum.provider.Provider;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class CrawlerService {

    public String parseWeb(Provider provider) {
        try {
            Document document =  Jsoup.connect(provider.getUrl()).get();
            Elements content = document.getElementsByClass("news");
            Elements views = document.getElementsByClass("view");
            for (Element view : views) {
                Element element = view.getElementsByTag("a").first();

                Element element1 = element.getElementsByTag("img").first();
                System.out.println(element1.attr("alt"));
                System.out.println(element.attr("href"));
                //System.out.println(element.attr("img"));


            }
            System.out.println("XXX");
            System.out.println("XXX");
            System.out.println("XXX");
            return "XXXXX";
        } catch (IOException e) {
            log.error(String.format("There are a problem with parsing website: %s", provider.getName()));
            e.printStackTrace();
            return null;
        }
    }

}
