package backend.shopapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressDTO {
    @NotBlank(message = "Country must not be blank")
    private String country;
    @NotBlank(message = "City must not be blank")
    private String city;
    @NotBlank(message = "Street must not be blank")
    private String street;
    @NotBlank(message = "House must not be blank")
    private String house;
    @NotBlank(message = "Apartment must not be blank")
    private String apartment;
}
