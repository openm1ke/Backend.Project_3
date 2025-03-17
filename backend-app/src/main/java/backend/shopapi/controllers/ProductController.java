package backend.shopapi.controllers;

import backend.shopapi.dto.ProductCreationDTO;
import backend.shopapi.dto.ProductDTO;
import backend.shopapi.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Product", description = "Product operations")
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    @Operation(summary = "Create new product", responses = {
        @ApiResponse(responseCode = "201", description = "Product created")
    })
    public ResponseEntity<ProductDTO> createProduct(@Validated @RequestBody ProductCreationDTO productCreationDTO) {
        ProductDTO product = productService.createProduct(productCreationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping("/products")
    @Operation(summary = "Get all products", responses = {
        @ApiResponse(responseCode = "200", description = "Products found")
    })
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("products/{id}")
    @Operation(summary = "Get product by ID", responses = {
        @ApiResponse(responseCode = "200", description = "Product found"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("products/{id}")
    @Operation(summary = "Delete product by ID", responses = {
        @ApiResponse(responseCode = "204", description = "Product deleted"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("products/{id}")
    @Operation(summary = "Update product by ID", responses = {
        @ApiResponse(responseCode = "200", description = "Product updated"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody Integer quantity) {
        return ResponseEntity.ok(productService.decreaseAvailableStock(id, quantity));
    }
}
