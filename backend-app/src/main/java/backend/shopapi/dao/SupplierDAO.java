package backend.shopapi.dao;

import backend.shopapi.models.Supplier;

import java.util.List;

public interface SupplierDAO {
    List<Supplier> findAll();
    Supplier findById(long id);
    Supplier save(Supplier supplier);
    void deleteById(long id);
    Supplier updateSupplierAddress(Long supplier_id, Long address_id);
}
