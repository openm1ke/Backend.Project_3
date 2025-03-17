package backend.shopapi.controllers;

import backend.shopapi.dto.ProductImageDTO;
import backend.shopapi.services.ProductImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/images")
@Tag(name = "Product Image", description = "The Product Image API")
public class ProductImageController {

    ProductImageService productImageService;

    @Autowired
    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @GetMapping
    @Operation(summary = "Get all product images", responses = {
        @ApiResponse(responseCode = "200", description = "Product images found")
    })
    public ResponseEntity<List<ProductImageDTO>> getAllProductImages() {
        return ResponseEntity.ok(productImageService.getAllProductImages());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product image by ID", responses = {
        @ApiResponse(responseCode = "200", description = "Product image found"),
        @ApiResponse(responseCode = "404", description = "Product image not found")
    })
    public ResponseEntity<byte[]> getProductImageById(@PathVariable UUID id) {
        byte[] image = productImageService.getProductImageById(id).getImage();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(image);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create new product image", responses = {
        @ApiResponse(responseCode = "201", description = "Product image created"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<ProductImageDTO> createProductImage(@RequestParam("image") MultipartFile image) {
        ProductImageDTO createdProductImage = productImageService.createProductImage(image);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProductImage);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update product image by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Product image updated"),
            @ApiResponse(responseCode = "404", description = "Product image not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<ProductImageDTO> updateProductImage(
            @PathVariable UUID id,
            @RequestParam("image") MultipartFile image) {
        ProductImageDTO updatedProductImage = productImageService.updateProductImage(id, image);
        return ResponseEntity.ok(updatedProductImage);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product image by ID", responses = {
        @ApiResponse(responseCode = "204", description = "Product image deleted"),
        @ApiResponse(responseCode = "404", description = "Product image not found")
    })
    public ResponseEntity<Void> deleteProductImageById(@PathVariable UUID id) {
        productImageService.deleteProductImageById(id);
        return ResponseEntity.noContent().build();
    }
}
