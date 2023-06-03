package bjit.ursa.authserver.controller;

import bjit.ursa.authserver.exception.AccountAlreadyExists;
import bjit.ursa.authserver.model.APIResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({AccountAlreadyExists.class})
    public APIResponse AccountExists(Exception ex){
        return APIResponse.builder()
                 .error_message(ex.getMessage())
                 .build();

    }
}
