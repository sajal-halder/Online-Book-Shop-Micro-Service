package bjit.ursa.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<InventoryRepository, Long> {
    public InventoryRepository findById(String bookId);
}
