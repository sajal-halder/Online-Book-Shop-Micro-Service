package bjit.ursa.inventoryservice.service;

import bjit.ursa.inventoryservice.entity.InventoryEntity;
import bjit.ursa.inventoryservice.model.APIResponse;
import org.springframework.http.ResponseEntity;

public interface InventoryService {
    ResponseEntity<APIResponse> updateBooks(Long bookId, InventoryEntity inventoryEntity);

    ResponseEntity<APIResponse> fetchId(Long bookId);

    ResponseEntity<APIResponse> fetchAllData();

    ResponseEntity<APIResponse> deleteInventoryById(Long bookId);

}
