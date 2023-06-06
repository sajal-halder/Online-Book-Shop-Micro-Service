package bjit.ursa.inventoryservice.service.Implementation;

import bjit.ursa.inventoryservice.entity.InventoryEntity;
import bjit.ursa.inventoryservice.exception.InventoryServiceException;
import bjit.ursa.inventoryservice.model.APIResponse;
import bjit.ursa.inventoryservice.repository.InventoryRepository;
import bjit.ursa.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImp implements InventoryService {

    private final InventoryRepository inventoryRepository;

//    public ResponseEntity<Object> create(InventoryEntity inventoryEntity) {
//        InventoryEntity bookEntity = InventoryEntity.builder()
//                .bookId(inventoryEntity.getBookId())
//                .bookPrice(inventoryEntity.getBookPrice())
//                .bookQuantity(inventoryEntity.getBookQuantity())
//                .build();
//        InventoryEntity savedInventoryEntity = inventoryRepository.save(bookEntity);
//        return new ResponseEntity<>(savedInventoryEntity, HttpStatus.CREATED);
//    }

    @Transactional
    @Override
    public ResponseEntity<APIResponse> updateBooks(Long bookId, InventoryEntity inventoryEntity) {
        try {
            Optional<InventoryEntity> updateInventory = inventoryRepository.findById(bookId);

            if (updateInventory.isPresent()) {
                InventoryEntity Inventory = updateInventory.get();
                // Update the book entity with the new values from the request model
                Inventory.setBookPrice(inventoryEntity.getBookPrice());
                Inventory.setBookQuantity(inventoryEntity.getBookQuantity());

                // Save the updated book entity
                InventoryEntity updatedBook = inventoryRepository.save(Inventory);


                APIResponse apiResponse = APIResponse.builder()
                        .data(updatedBook)
                        .build();

                return ResponseEntity.ok(apiResponse);

            } else {
                InventoryEntity bookEntity = InventoryEntity.builder()
                        .bookId(inventoryEntity.getBookId())
                        .bookPrice(inventoryEntity.getBookPrice())
                        .bookQuantity(inventoryEntity.getBookQuantity())
                        .build();
                InventoryEntity savedInventoryEntity = inventoryRepository.save(bookEntity);
                APIResponse apiResponse = APIResponse.builder()
                        .data(savedInventoryEntity)
                        .build();
                return ResponseEntity.ok(apiResponse);
            }
        } catch (Exception e) {
            throw new InventoryServiceException("An error occurred while retrieving the book by ID");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<APIResponse> fetchId(Long bookId) {
        try {
            Optional<InventoryEntity> findIdInventory = inventoryRepository.findById(bookId);
            if (findIdInventory.isPresent()) {
                APIResponse<InventoryEntity> apiResponse = APIResponse.<InventoryEntity>builder()
                        .data((InventoryEntity) findIdInventory.get())
                        .build();

                return ResponseEntity.ok(apiResponse);

            } else {
                throw new InventoryServiceException("Not Found");
            }
        } catch (Exception e) {
            throw new InventoryServiceException("Error");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<APIResponse> fetchAllData() {
        List<InventoryEntity> inventoryBooks = inventoryRepository.findAll();
        if (inventoryBooks.isEmpty()) {
            throw new InventoryServiceException("None");
        }
//        return inventoryBooks;
        List<InventoryEntity> inventoryResponses = new ArrayList<>();
        inventoryBooks.forEach(inventoryEntity -> inventoryResponses.add(
                InventoryEntity.builder()
                        .bookId(inventoryEntity.getBookId())
                        .bookPrice(inventoryEntity.getBookPrice())
                        .bookQuantity(inventoryEntity.getBookQuantity())
                        .build()
        ));
        APIResponse apiResponse = APIResponse.builder()
                .data(inventoryResponses)
                .build();

        //Return the ResponseEntity with the APIResponse
        return ResponseEntity.ok(apiResponse);

    }

    public ResponseEntity<APIResponse> deleteInventoryById(Long bookId) {
        try {
            if (inventoryRepository.existsById(bookId)) {
                inventoryRepository.deleteById(bookId);
                APIResponse apiResponse = APIResponse.builder()
                        .data("Successfully deleted")
                        .build();
                return ResponseEntity.ok(apiResponse);
            } else {
                throw new InventoryServiceException("Not Found");
            }
        } catch (Exception e) {
            throw new InventoryServiceException("Error");
        }
    }
}
