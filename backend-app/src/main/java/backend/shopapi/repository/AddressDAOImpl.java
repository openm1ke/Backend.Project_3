package backend.shopapi.repository;

import backend.shopapi.dao.AddressDAO;
import backend.shopapi.models.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class AddressDAOImpl implements AddressDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AddressDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Address findByCountryAndCityAndStreetAndHouseAndApartment(Address address) {
        String country = address.getCountry();
        String city = address.getCity();
        String street = address.getStreet();
        String house = address.getHouse();
        String apartment = address.getApartment();
        String sql = "SELECT * FROM addresses WHERE country = ? AND city = ? AND street = ? AND house = ? AND apartment = ?";
        List<Address> addresses = jdbcTemplate.query(sql, new AddressRowMapper(), country, city, street, house, apartment);
        if (!addresses.isEmpty()) {
            return addresses.get(0);
        }
        return save(address);
    }

    @Override
    public Address save(Address address) {
        String sql = "INSERT INTO addresses (country, city, street, house, apartment) VALUES (?, ?, ?, ?, ?) RETURNING id";
        Long generatedId = jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                return rs.getLong("id");
            }
            return null;
        }, address.getCountry(), address.getCity(), address.getStreet(), address.getHouse(), address.getApartment());
        address.setId(generatedId);
        return address;
    }

    private static class AddressRowMapper implements RowMapper<Address> {

        @Override
        public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
            Address address = new Address();
            address.setId(rs.getLong("id"));
            address.setCountry(rs.getString("country"));
            address.setCity(rs.getString("city"));
            address.setHouse(rs.getString("house"));
            address.setApartment(rs.getString("apartment"));
            address.setStreet(rs.getString("street"));
            return address;
        }
    }

}
