package ir.netrira.core.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.netrira.core.ResponseConstant;
import ir.netrira.core.ResponseConstantMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DataResponse extends Response {

    @JsonProperty("resultData")
    private Object resultData;

    public static DataResponse SUCCESS_RESPONSE =
            new DataResponse(ResponseConstant.SC_OK, ResponseConstantMessage.SC_OK, null);

    public DataResponse(int code, String message, Object resultData) {
        super(code, message);
        this.resultData = resultData;
    }
}
