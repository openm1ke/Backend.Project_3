package backend.shopapi.services;

import backend.shopapi.dao.ProductImageDAO;
import backend.shopapi.dto.ProductImageDTO;
import backend.shopapi.exceptions.ImageIsEmptyException;
import backend.shopapi.exceptions.ImageUploadException;
import backend.shopapi.exceptions.ProductImageNotFoundException;
import backend.shopapi.mappers.ProductImageMapper;
import backend.shopapi.models.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductImageService {

    private final ProductImageDAO productImageDAO;
    private final ProductImageMapper productImageMapper;

    @Autowired
    public ProductImageService(ProductImageDAO productImageDAO, ProductImageMapper productImageMapper) {
        this.productImageDAO = productImageDAO;
        this.productImageMapper = productImageMapper;
    }

    public ProductImageDTO updateProductImage(UUID id, MultipartFile image) {
        ProductImage productImage = productImageDAO.findByProductId(id);
        if(productImage == null) {
            throw new ProductImageNotFoundException("Product image with id " + id + " not found.");
        }
        try {
            byte[] data = image.getBytes();
            productImage.setImage(data);
            productImage = productImageDAO.update(productImage);
            return productImageMapper.toDto(productImage);
        } catch (IOException e) {
            throw new ImageUploadException("Image upload error");
        }
    }

    public ProductImageDTO createProductImage(MultipartFile image) {
        if(image.isEmpty()) {
            throw new ImageIsEmptyException("Image is empty.");
        }
        try {
            byte[] data = image.getBytes();
            ProductImage productImage = new ProductImage();
            productImage.setId(UUID.randomUUID());
            productImage.setImage(data);
            productImage = productImageDAO.save(productImage);
            return productImageMapper.toDto(productImage);
        } catch (IOException e) {
            throw new ImageUploadException("Image upload error");
        }
    }

    public ProductImageDTO getProductImageById(UUID id) {
        ProductImage productImage = productImageDAO.findByProductId(id);
        if(productImage == null) {
            throw new ProductImageNotFoundException("Product image with id " + id + " not found.");
        }
        return productImageMapper.toDto(productImage);
    }

    public void deleteProductImageById(UUID id) {
        ProductImage productImage = productImageDAO.findByProductId(id);
        if(productImage == null) {
            throw new ProductImageNotFoundException("Product image with id " + id + " not found.");
        }
        productImageDAO.deleteByProductId(id);
    }

    public List<ProductImageDTO> getAllProductImages() {
        List<ProductImage> productImages = productImageDAO.findAll();
        List<ProductImageDTO> productImageDTOList = new ArrayList<>();
        for(ProductImage image : productImages) {
            productImageDTOList.add(productImageMapper.toDto(image));
        }
        return productImageDTOList;
    }
}
