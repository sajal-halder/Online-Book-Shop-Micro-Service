package bjit.ursa.inventoryservice.exception;

import bjit.ursa.inventoryservice.entity.InventoryEntity;
import bjit.ursa.inventoryservice.model.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class InventoryServiceException extends RuntimeException {
    public InventoryServiceException(String msg) {
        super(msg);
    }

    @ControllerAdvice
    public static class GlobalExceptionHandler {
        @ExceptionHandler({InventoryServiceException.class})
        public ResponseEntity<APIResponse> InventoryServiceExceptionHandler(Exception ex) {
            APIResponse<InventoryEntity> apiResponse = APIResponse.<InventoryEntity>builder()
                    .error_message(ex.getMessage())
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
    }
}