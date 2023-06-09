package bjit.ursa.bookservice.exception;

import bjit.ursa.bookservice.entity.BookEntity;
import bjit.ursa.bookservice.model.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({BookServiceException.class})
    public ResponseEntity<APIResponse> BookServiceExceptionHandler(Exception ex) {
        APIResponse<BookEntity> apiResponse = new APIResponse<>(null, ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
}