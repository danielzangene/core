package ir.netrira.core.exception;

import ir.netrira.core.ResponseConstant;
import ir.netrira.core.ResponseConstantMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity defaultExceptionHandler(Exception e) {
        Response response = new Response(ResponseConstant.INTERNAL_ERROR, ResponseConstantMessage.INTERNAL_ERROR);
        return ResponseEntity.ok().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity defaultMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Response response = new Response(ResponseConstant.SC_BAD_REQUEST, ResponseConstantMessage.SC_BAD_REQUEST);
        return ResponseEntity.ok().body(response);
    }

    @ExceptionHandler(AccessException.class)
//    HttpMessageNotReadableException
    public ResponseEntity accessExceptionHandler(AccessException e) {
        Response response = new Response(ResponseConstant.SC_METHOD_NOT_ALLOWED, getAccessMessageHandler(e.getUseCaseName()));
        return ResponseEntity.ok().body(response);
    }

    private String getAccessMessageHandler(String useCaseName) {
        return String.format(ResponseConstantMessage.SC_METHOD_NOT_ALLOWED, useCaseName);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity businessExceptionHandler(BusinessException e) {
        Response response = new Response(e.getCode(), e.getMessage());
        return ResponseEntity.ok().body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity badCredentialsExceptionHandler(BadCredentialsException e) {
        Response response = new Response(ResponseConstant.SC_BAD_REQUEST, ResponseConstantMessage.INVALID_LOGIN_DATA);
        return ResponseEntity.ok().body(response);
    }


}
