package com.oskarro.muzikum.config;

import com.oskarro.muzikum.plugin.PluginService;
import com.oskarro.muzikum.plugin.PluginServiceImpl;
import org.springframework.context.annotation.Bean;

@org.springframework.boot.test.context.TestConfiguration
public class TestConfiguration {

    @Bean
    public PluginService pluginService() {
        return new PluginServiceImpl();
    }
}
