package ir.netrira.core.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ErrorResponse extends Response {

    @JsonProperty("errorList")
    List<ErrorObject> errorList;

    public ErrorResponse(int code, String message, List<ErrorObject> errorList) {
        super(code, message);
        this.errorList = errorList;
    }
}
