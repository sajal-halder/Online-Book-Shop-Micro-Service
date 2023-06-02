package bjit.ursa.inventoryservice.controller;

import bjit.ursa.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book-inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/update/bookId")
    public ResponseEntity<Object> update() {

    }

    @GetMapping("/book-id")
    public ResponseEntity<Object> fetchId() {

    }

    @GetMapping("/getAll")
    public ResponseEntity<Object> fetchAllData() {

    }

    @DeleteMapping("/delete/bookId")
    public ResponseEntity<Object> fetch() {

    }

}
