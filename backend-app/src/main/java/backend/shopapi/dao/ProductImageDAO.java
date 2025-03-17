package backend.shopapi.dao;

import backend.shopapi.models.ProductImage;

import java.util.List;
import java.util.UUID;

public interface ProductImageDAO {
    ProductImage save(ProductImage productImage);
    ProductImage findByProductId(UUID productId);
    ProductImage update(ProductImage productImage);
    void deleteByProductId(UUID productId);
    List<ProductImage> findAll();
}
