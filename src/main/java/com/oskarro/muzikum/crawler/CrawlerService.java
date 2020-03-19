package com.oskarro.muzikum.crawler;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.oskarro.muzikum.provider.Provider;
import geb.Module;
import geb.Page;
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

    public String getWeb(Provider provider) {
        try {
            final WebClient webClient = new WebClient();
            webClient.getOptions().setThrowExceptionOnScriptError(false);

            final HtmlPage page = webClient.getPage(provider.getUrl());
            final HtmlForm form = page.getFormByName("myform");
            final HtmlSubmitInput button = form.getInputByName("submitbutton");

            System.out.println("XXXX -- " + page.getTitleText());
            return "xxx";
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
