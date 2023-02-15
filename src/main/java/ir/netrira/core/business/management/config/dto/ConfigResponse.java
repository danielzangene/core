package ir.netrira.core.business.management.config.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.netrira.core.models.application.utils.Config;
import lombok.Data;

import java.util.List;

@Data
public class ConfigResponse {
    @JsonProperty
    private Config config;
    @JsonProperty
    private List<Config> subConfig;

    public ConfigResponse(Config config, List<Config> subConfig) {
        this.config = config;
        this.subConfig = subConfig;
    }
}
