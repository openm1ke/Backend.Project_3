package backend.shopapi.mappers;

import backend.shopapi.dto.AddressDTO;
import backend.shopapi.models.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressDTO toDto(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCountry(address.getCountry());
        addressDTO.setCity(address.getCity());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setApartment(address.getApartment());
        addressDTO.setHouse(address.getHouse());
        return addressDTO;
    }

    public Address toEntity(AddressDTO addressDTO) {
        Address address = new Address();
        address.setCountry(addressDTO.getCountry());
        address.setCity(addressDTO.getCity());
        address.setStreet(addressDTO.getStreet());
        address.setApartment(addressDTO.getApartment());
        address.setHouse(addressDTO.getHouse());
        return address;
    }
}
