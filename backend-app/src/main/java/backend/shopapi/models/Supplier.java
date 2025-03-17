package backend.shopapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Supplier {
    private Long id;
    private String name;
    private Long address_id;
    private String phone_number;
}
