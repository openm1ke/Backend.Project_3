package backend.shopapi.repository;

import backend.shopapi.dao.ProductDAO;
import backend.shopapi.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductDAOImpl implements ProductDAO {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product save(Product product) {
        String sql = "INSERT INTO Products (name, category, price, available_stock, last_update_date, supplier_id, image_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getAvailable_stock());
            ps.setTimestamp(5, Timestamp.valueOf(product.getLast_update_date()));
            ps.setLong(6, product.getSupplier_id());
            ps.setString(7, (product.getImage_id() != null) ? product.getImage_id().toString() : null);
            return ps;
        }, keyHolder);
        product.setId(keyHolder.getKey().longValue());
        return product;
    }

    @Override
    public Product findById(Long id) {
        String sql = "SELECT * FROM Products WHERE id = ?";
        List<Product> products = jdbcTemplate.query(sql, new ProductRowMapper(), id);
        if(products.isEmpty()) {
            return null;
        }
        return products.get(0);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM Products";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    @Override
    public Product decreaseAvailableStock(Long id, Timestamp timestamp, Integer quantity) {
        String sql = "UPDATE Products SET available_stock = ?, last_update_date = ? WHERE id = ?";
        jdbcTemplate.update(sql, quantity, timestamp, id);
        return findById(id);
    }

    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setName(rs.getString("name"));
            product.setCategory(rs.getString("category"));
            product.setPrice(rs.getDouble("price"));
            product.setAvailable_stock(rs.getInt("available_stock"));
            product.setLast_update_date(rs.getTimestamp("last_update_date").toLocalDateTime());
            product.setSupplier_id(rs.getLong("supplier_id"));

            String imageId = rs.getString("image_id");
            if (imageId != null) {
                product.setImage_id(UUID.fromString(imageId));
            }

            return product;
        }
    }
}
