package backend.shopapi.mappers;

import backend.shopapi.dto.ProductImageDTO;
import backend.shopapi.models.ProductImage;
import org.springframework.stereotype.Component;

@Component
public class ProductImageMapper {

    public ProductImageDTO toDto(ProductImage productImage) {
        ProductImageDTO productImageDTO = new ProductImageDTO();
        productImageDTO.setId(productImage.getId());
        productImageDTO.setImage(productImage.getImage());
        return productImageDTO;
    }

    public ProductImage toEntity(ProductImageDTO productImageDTO) {
        ProductImage productImage = new ProductImage();
        productImage.setImage(productImageDTO.getImage());
        return productImage;
    }
}
