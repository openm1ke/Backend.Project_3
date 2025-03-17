package backend.shopapi.repository;

import backend.shopapi.dao.ProductImageDAO;
import backend.shopapi.models.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductImageDAOImpl implements ProductImageDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductImageDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ProductImage save(ProductImage productImage) {
        String sql = "INSERT INTO Product_images (id, image) VALUES (?, ?)";
        jdbcTemplate.update(sql, productImage.getId(), productImage.getImage());
        return productImage;
    }

    @Override
    public ProductImage update(ProductImage productImage) {
        String sql = "UPDATE Product_images SET image = ? WHERE id = ?";
        jdbcTemplate.update(sql, productImage.getImage(), productImage.getId());
        return productImage;
    }

    @Override
    public ProductImage findByProductId(UUID productId) {
        String sql = "SELECT * FROM Product_images WHERE id = ?";
        List<ProductImage> productImages = jdbcTemplate.query(sql, new ProductImageRowMapper(), productId);
        if(productImages.isEmpty()) {
            return null;
        }
        return productImages.get(0);
    }

    @Override
    public void deleteByProductId(UUID productId) {
        String sql = "DELETE FROM Product_images WHERE id = ?";
        jdbcTemplate.update(sql, productId);
    }

    @Override
    public List<ProductImage> findAll() {
        String sql = "SELECT * FROM Product_images";
        return jdbcTemplate.query(sql, new ProductImageRowMapper());
    }

    private static class ProductImageRowMapper implements RowMapper<ProductImage> {

        @Override
        public ProductImage mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProductImage productImage = new ProductImage();
            productImage.setId(UUID.fromString(rs.getString("id")));
            productImage.setImage(rs.getBytes("image"));
            return productImage;
        }
    }
}
