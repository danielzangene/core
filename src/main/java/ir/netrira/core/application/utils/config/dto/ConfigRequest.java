package ir.netrira.core.application.utils.config.dto;

import javax.validation.constraints.NotBlank;

public class ConfigRequest {
    private String configId;

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }
}
