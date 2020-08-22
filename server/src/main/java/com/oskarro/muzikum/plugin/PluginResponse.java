package com.oskarro.muzikum.plugin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PluginResponse {
    private TypeUrl typeUrl;
}
