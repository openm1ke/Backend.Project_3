package backend.shopapi.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Product {
    private Long id;
    private String name;
    private String category;
    private Double price;
    private Integer available_stock;
    private LocalDateTime last_update_date;
    private Long supplier_id;
    private UUID image_id;
}
