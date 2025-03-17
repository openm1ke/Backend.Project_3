package backend.shopapi.services;

import backend.shopapi.dao.ProductDAO;
import backend.shopapi.dao.ProductImageDAO;
import backend.shopapi.dto.ProductCreationDTO;
import backend.shopapi.dto.ProductDTO;
import backend.shopapi.exceptions.ProductNotFoundException;
import backend.shopapi.exceptions.ProductOutOfStockException;
import backend.shopapi.mappers.ProductImageMapper;
import backend.shopapi.mappers.ProductMapper;
import backend.shopapi.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    ProductDAO productDAO;
    ProductMapper productMapper;
    ProductImageDAO productImageDAO;
    ProductImageMapper productImageMapper;

    @Autowired
    public ProductService(ProductDAO productDAO, ProductMapper productMapper, ProductImageDAO productImageDAO, ProductImageMapper productImageMapper) {
        this.productMapper = productMapper;
        this.productDAO = productDAO;
        this.productImageDAO = productImageDAO;
        this.productImageMapper = productImageMapper;
    }

    public ProductDTO decreaseAvailableStock(Long id, Integer quantity) {
        Product product = productDAO.findById(id);
        if(product == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found.");
        }

        if(product.getAvailable_stock() < quantity) {
            throw new ProductOutOfStockException("Product with id " + id + " is out of stock.");
        }

        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);

        Integer availableStock = product.getAvailable_stock() - quantity;

        product = productDAO.decreaseAvailableStock(id, timestamp, availableStock);
        return productMapper.toDto(product);
    }

    public ProductDTO getProductById(Long id) {
        Product product = productDAO.findById(id);
        if(product == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found.");
        }
        return productMapper.toDto(product);
    }

    public void deleteProduct(Long id) {
        Product product = productDAO.findById(id);
        if (product == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found.");
        }
        productDAO.deleteById(id);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productDAO.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();

        for (Product product : products) {
            productDTOs.add(productMapper.toDto(product));
        }

        return productDTOs;
    }

    public ProductDTO createProduct(ProductCreationDTO productCreationDTO) {
        Product product = productMapper.toEntity(productCreationDTO);
        LocalDateTime localDateTime = LocalDateTime.now();
        product.setLast_update_date(localDateTime);
        product = productDAO.save(product);
        return productMapper.toDto(product);
    }
}
