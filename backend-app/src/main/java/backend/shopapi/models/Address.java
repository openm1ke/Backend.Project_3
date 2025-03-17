package backend.shopapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private Long id;
    private String country;
    private String city;
    private String street;
    private String house;
    private String apartment;
}
