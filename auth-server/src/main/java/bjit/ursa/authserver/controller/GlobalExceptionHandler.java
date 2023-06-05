package bjit.ursa.authserver.controller;

import bjit.ursa.authserver.exception.AccountAlreadyExists;
import bjit.ursa.authserver.exception.InvalidAuthenticationCredentials;
import bjit.ursa.authserver.model.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({AccountAlreadyExists.class})
    public ResponseEntity<APIResponse> AccountExists(Exception ex) {
        return new ResponseEntity<>(APIResponse.builder().error_message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidAuthenticationCredentials.class})
    public ResponseEntity<APIResponse> InvalidCredentials(Exception ex) {
        return  new ResponseEntity<>( APIResponse.builder().error_message(ex.getMessage()).build(), HttpStatus.NOT_FOUND);
    }
}
