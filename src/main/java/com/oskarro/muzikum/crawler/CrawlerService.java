package com.oskarro.muzikum.crawler;

import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLSpanElement;
import com.oskarro.muzikum.provider.Provider;
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

    // TODO Enum for choosing music genre

    public String getWeb(Provider provider) {
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
                HtmlAnchor a = (HtmlAnchor) obj;
                System.out.println(a.getTextContent().trim());
                System.out.println(a.getHrefAttribute());
            }
            return "_____________________";
        } catch (IOException e) {
            e.printStackTrace();
            return "errrrrrror!";
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
