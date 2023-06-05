package bjit.ursa.inventoryservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "bookInventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;
    private double bookPrice;
    private Integer bookQuantity;
}
