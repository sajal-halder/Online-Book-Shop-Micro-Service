package bjit.ursa.inventoryservice.controller;

import bjit.ursa.inventoryservice.entity.InventoryEntity;
import bjit.ursa.inventoryservice.model.APIResponse;
import bjit.ursa.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/update/{bookId}")
    public ResponseEntity<APIResponse<?>> updateInventory(@PathVariable Long bookId, @RequestBody InventoryEntity inventoryEntity) {
        return inventoryService.updateBooks(bookId, inventoryEntity);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<APIResponse<?>> fetchId(@PathVariable Long bookId) {
        return inventoryService.fetchId(bookId);

    }

    @PostMapping("/")
    public ResponseEntity<APIResponse<?>> fetchAllData(@RequestBody List<Long> ids) {
        return inventoryService.fetchAllData(ids);
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<APIResponse<?>> deleteInventoryById(@PathVariable Long bookId) {
        return inventoryService.deleteInventoryById(bookId);
    }
}
