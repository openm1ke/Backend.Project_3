package backend.shopapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductCreationDTO {
    @NotBlank(message = "Name must not be blank")
    private String name;
    @NotBlank(message = "Category must not be blank")
    private String category;
    @NotNull(message = "Price must not be null")
    private Double price;
    @NotNull(message = "Available stock must not be null")
    private Integer available_stock;
    @NotNull(message = "Supplier id must not be null")
    private Long supplier_id;
    private UUID image_id;
}
