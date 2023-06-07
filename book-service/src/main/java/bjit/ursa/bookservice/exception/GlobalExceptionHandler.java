package bjit.ursa.bookservice.exception;

import bjit.ursa.bookservice.entity.BookEntity;
import bjit.ursa.bookservice.model.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BookServiceException.class, ArithmeticException.class})
    public ResponseEntity<APIResponse> returnNotFoundException(Exception ex) {
        if(ex instanceof BookServiceException) {

            APIResponse<BookEntity> apiResponse = new APIResponse<>(null,ex.getMessage());

            // Return the ResponseEntity with the APIResponse
            return ResponseEntity.ok(apiResponse);

        } else {
            // Some other operation
            APIResponse<BookEntity> apiResponse = new APIResponse<>(null,ex.getMessage());
            return ResponseEntity.ok(apiResponse);
        }
    }
}
