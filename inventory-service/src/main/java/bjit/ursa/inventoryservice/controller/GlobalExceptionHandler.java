package bjit.ursa.inventoryservice.controller;

import bjit.ursa.inventoryservice.entity.InventoryEntity;
import bjit.ursa.inventoryservice.exception.InventoryServiceException;
import bjit.ursa.inventoryservice.model.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({InventoryServiceException.class, ArithmeticException.class})
    public ResponseEntity<APIResponse> returnNotFoundException(Exception ex) {
        if (ex instanceof InventoryServiceException) {

            APIResponse<InventoryEntity> apiResponse = APIResponse.<InventoryEntity>builder()
                    .error_message(ex.getMessage())
                    .build();

            // Return the ResponseEntity with the APIResponse
            return ResponseEntity.ok(apiResponse);

        } else {
            // Some other operation
            APIResponse<InventoryEntity> apiResponse = APIResponse.<InventoryEntity>builder()
                    .error_message(ex.getMessage())
                    .build();
            return ResponseEntity.ok(apiResponse);
        }
    }
}
