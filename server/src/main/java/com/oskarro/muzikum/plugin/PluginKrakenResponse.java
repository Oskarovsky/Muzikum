package com.oskarro.muzikum.plugin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PluginKrakenResponse{
    private String url;
    private String title;
    private String size;
    private String numberOfDownloads;
    private String views;
    private String uploadDate;
    private String hash;
}
