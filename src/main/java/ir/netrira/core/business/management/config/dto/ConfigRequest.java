package ir.netrira.core.business.management.config.dto;

import javax.validation.constraints.NotBlank;

public class ConfigRequest {
    private Long configId;

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }
}
