package backend.shopapi.dto;

import backend.shopapi.annotation.ValidPhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierCreationDTO {
    @NotBlank(message = "Name must not be blank")
    private String name;
    @NotBlank(message = "Phone number must not be blank")
    @ValidPhoneNumber
    private String phone_number;
    @Valid
    private AddressDTO address;
}
