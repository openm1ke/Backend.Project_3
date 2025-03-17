package backend.shopapi.repository;

import backend.shopapi.dao.SupplierDAO;
import backend.shopapi.models.Supplier;
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
public class SupplierDAOImpl implements SupplierDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SupplierDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Supplier> findAll() {
        String sql = "SELECT * FROM suppliers";
        return jdbcTemplate.query(sql, new SupplierRowMapper());
    }

    @Override
    public Supplier findById(long id) {
        String sql = "SELECT * FROM suppliers WHERE id = ?";
        List<Supplier> suppliers = jdbcTemplate.query(sql, new SupplierRowMapper(), id);
        if(suppliers.isEmpty()) {
            return null;
        }
        return suppliers.get(0);
    }

    @Override
    public Supplier updateSupplierAddress(Long supplier_id, Long address_id) {
        String sql = "UPDATE suppliers SET address_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, address_id, supplier_id);
        return findById(supplier_id);
    }

    @Override
    public Supplier save(Supplier supplier) {
        String sql = "INSERT INTO suppliers (name, address_id, phone_number) VALUES (?, ?, ?) RETURNING id";
        Long generatedId = jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                return rs.getLong("id");
            }
            return null;
        }, supplier.getName(), supplier.getAddress_id(), supplier.getPhone_number());

        if (generatedId != null) {
            supplier.setId(generatedId);
        }
        return supplier;
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM suppliers WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public static class SupplierRowMapper implements RowMapper<Supplier> {

        @Override
        public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
            Supplier supplier = new Supplier();
            supplier.setId(rs.getLong("id"));
            supplier.setName(rs.getString("name"));
            supplier.setAddress_id(rs.getLong("address_id"));
            supplier.setPhone_number(rs.getString("phone_number"));
            return supplier;
        }
    }
}
