package backend.shopapi.dao;

import backend.shopapi.models.Product;

import java.sql.Timestamp;
import java.util.List;

public interface ProductDAO {
    Product save(Product product);
    Product findById(Long id);
    void deleteById(Long id);
    List<Product> findAll();
    Product decreaseAvailableStock(Long id, Timestamp timestamp, Integer quantity);
}
