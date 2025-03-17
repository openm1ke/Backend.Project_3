package backend.shopapi.mappers;

import backend.shopapi.dto.SupplierCreationDTO;
import backend.shopapi.dto.SupplierDTO;
import backend.shopapi.models.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public Supplier toEntity(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierDTO.getName());
        supplier.setAddress_id(supplierDTO.getAddress_id());
        supplier.setPhone_number(supplierDTO.getPhone_number());
        return supplier;
    }

    public SupplierDTO toDto(Supplier supplier) {
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setId(supplier.getId());
        supplierDTO.setName(supplier.getName());
        supplierDTO.setAddress_id(supplier.getAddress_id());
        supplierDTO.setPhone_number(supplier.getPhone_number());
        return supplierDTO;
    }

    public Supplier toEntity(SupplierCreationDTO supplierCreationDTO) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierCreationDTO.getName());
        supplier.setPhone_number(supplierCreationDTO.getPhone_number());
        return supplier;
    }
}
