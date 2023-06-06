package bjit.ursa.bookservice.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryEntity {
    private Long bookId;
    private double bookPrice;
    private Integer bookQuantity;
}
