package bjit.ursa.bookservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BookServiceException.class, ArithmeticException.class})
    public ResponseEntity<Object> returnNotFoundException(Exception ex) {
        if(ex instanceof BookServiceException) {
            // Some operation
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } else {
            // Some other operation
            return new ResponseEntity<>(ex.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
