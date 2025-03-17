package backend.shopapi.dao;

import backend.shopapi.models.Address;

public interface AddressDAO {
    Address save(Address address);
    Address findByCountryAndCityAndStreetAndHouseAndApartment(Address address);
}
