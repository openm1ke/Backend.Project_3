package backend.shopapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierDTO {
    private Long id;
    private String name;
    private Long address_id;
    private String phone_number;
}
