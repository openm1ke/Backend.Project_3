package backend.shopapi.controllers;

import backend.shopapi.dto.AddressDTO;
import backend.shopapi.dto.SupplierCreationDTO;
import backend.shopapi.dto.SupplierDTO;
import backend.shopapi.services.SupplierService;
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
@RequestMapping("/api/v1/suppliers")
@Tag(name = "Supplier Controller", description = "Supplier Controller")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    @Operation(summary = "Get all suppliers", responses = {
        @ApiResponse(responseCode = "200", description = "Suppliers found")
    })
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get supplier by ID", responses = {
        @ApiResponse(responseCode = "200", description = "Supplier found"),
    })
    public ResponseEntity<SupplierDTO> getSupplier(@PathVariable Long id) {
        SupplierDTO supplier = supplierService.getSupplierById(id);
        return ResponseEntity.ok(supplier);
    }

    @PostMapping
    @Operation(summary = "Create new supplier", responses = {
        @ApiResponse(responseCode = "201", description = "Supplier created")
    })
    public ResponseEntity<SupplierDTO> createSupplier(@Validated @RequestBody SupplierCreationDTO supplierDTO) {
        SupplierDTO supplier = supplierService.createSupplier(supplierDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(supplier);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete supplier by ID", responses = {
        @ApiResponse(responseCode = "204", description = "Supplier deleted"),
        @ApiResponse(responseCode = "404", description = "Supplier not found")
    })
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update supplier address by ID", responses = {
        @ApiResponse(responseCode = "200", description = "Supplier address updated"),
        @ApiResponse(responseCode = "404", description = "Supplier not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<SupplierDTO> updateSupplierAddress(@PathVariable Long id,@Validated @RequestBody AddressDTO addressDTO) {
        SupplierDTO updatedSupplier = supplierService.updateSupplierAddress(id, addressDTO);
        return ResponseEntity.ok(updatedSupplier);
    }
}
