package ir.netrira.core.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorObject {
    @JsonProperty("code")
    String code;
    @JsonProperty("desc")
    String desc;
    @JsonProperty("paramName")
    String paramName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
}
