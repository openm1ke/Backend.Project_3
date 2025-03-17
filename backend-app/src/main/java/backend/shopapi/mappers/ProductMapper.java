package backend.shopapi.mappers;

import backend.shopapi.dto.ProductCreationDTO;
import backend.shopapi.dto.ProductDTO;
import backend.shopapi.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductCreationDTO productCreationDTO) {
        Product product = new Product();
        product.setName(productCreationDTO.getName());
        product.setCategory(productCreationDTO.getCategory());
        product.setPrice(productCreationDTO.getPrice());
        product.setAvailable_stock(productCreationDTO.getAvailable_stock());
        product.setSupplier_id(productCreationDTO.getSupplier_id());
        product.setImage_id(productCreationDTO.getImage_id());
        return product;
    }

    public Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        product.setAvailable_stock(productDTO.getAvailable_stock());
        product.setLast_update_date(productDTO.getLast_update_date());
        product.setSupplier_id(productDTO.getSupplier_id());
        product.setImage_id(productDTO.getImage_id());
        return product;
    }

    public ProductDTO toDto(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategory(product.getCategory());
        productDTO.setPrice(product.getPrice());
        productDTO.setAvailable_stock(product.getAvailable_stock());
        productDTO.setLast_update_date(product.getLast_update_date());
        productDTO.setSupplier_id(product.getSupplier_id());
        productDTO.setImage_id(product.getImage_id());
        return productDTO;
    }
}
