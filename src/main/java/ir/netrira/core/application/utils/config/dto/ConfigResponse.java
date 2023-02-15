package ir.netrira.core.application.utils.config.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.netrira.core.application.utils.config.ConfigModel;
import lombok.Data;

import java.util.List;

@Data
public class ConfigResponse {
    @JsonProperty
    private ConfigModel config;
    @JsonProperty
    private List<ConfigModel> subConfig;

    public ConfigResponse(ConfigModel config, List<ConfigModel> subConfig) {
        this.config = config;
        this.subConfig = subConfig;
    }
}
