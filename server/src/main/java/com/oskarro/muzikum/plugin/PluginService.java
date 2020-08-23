package com.oskarro.muzikum.plugin;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface PluginService {

    PluginKrakenResponse readJsonFromKrakenFiles(String jsonUrl) throws IOException, ParseException;

    String prepareScript(PluginKrakenResponse response);

    String getJsonUrlFromWebsiteUrl(String websiteUrl);

    String prepareScriptForZippyshare(String websiteUrl);
}
