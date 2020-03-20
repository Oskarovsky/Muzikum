package com.oskarro.muzikum.crawler;

import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLSpanElement;
import com.oskarro.muzikum.provider.Provider;
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

    public String getWeb(Provider provider) {
        try {
            final WebClient webClient = new WebClient();
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            final HtmlPage page = webClient.getPage(provider.getUrl());

            HtmlButton select = page.getFirstByXPath( "//div[@class='btn-group category']//button");
            select.click();
            HtmlButton select1 = page.getFirstByXPath( "//div[@class='btn-group category open']//button");

            HtmlUnorderedList htmlUnorderedList = page.getFirstByXPath( "//div[@class='btn-group category open']//ul");
            HtmlListItem listItem = (HtmlListItem) htmlUnorderedList.getChildNodes().get(1);

            HtmlElement htmlElement = htmlUnorderedList.getChildNodes().get(2).getFirstByXPath("//a");
            HtmlAnchor htmlAnchor1 = (HtmlAnchor) htmlElement;
            System.out.println(htmlAnchor1.getTextContent());

            htmlAnchor1.click();
            webClient.waitForBackgroundJavaScript(5 * 1000);
            System.out.println(listItem);

            final List<HtmlElement> spanElements = page.getByXPath("//span[@class='news-title']//a");

            for (Object obj : spanElements) {
                HtmlAnchor a = (HtmlAnchor) obj;
                System.out.println(a.getTextContent().trim());
                System.out.println(a.getHrefAttribute().trim());
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
