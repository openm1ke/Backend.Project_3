package backend.shopapi.services;

import backend.shopapi.dao.AddressDAO;
import backend.shopapi.dao.SupplierDAO;
import backend.shopapi.dto.AddressDTO;
import backend.shopapi.dto.SupplierCreationDTO;
import backend.shopapi.dto.SupplierDTO;
import backend.shopapi.exceptions.AddressNotProvidedException;
import backend.shopapi.exceptions.SupplierNotFoundException;
import backend.shopapi.mappers.AddressMapper;
import backend.shopapi.mappers.SupplierMapper;
import backend.shopapi.models.Address;
import backend.shopapi.models.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierService {

    private final SupplierDAO supplierDAO;
    private final AddressDAO addressDAO;
    private final SupplierMapper supplierMapper;
    private final AddressMapper addressMapper;

    @Autowired
    public SupplierService(SupplierDAO supplierDAO, AddressDAO addressDAO, SupplierMapper supplierMapper, AddressMapper addressMapper) {
        this.supplierDAO = supplierDAO;
        this.addressDAO = addressDAO;
        this.supplierMapper = supplierMapper;
        this.addressMapper = addressMapper;
    }

    public SupplierDTO getSupplierById(long id) {
        Supplier supplier = supplierDAO.findById(id);
        if(supplier == null) {
            throw new SupplierNotFoundException("Supplier with id " + id + " not found.");
        }
        return supplierMapper.toDto(supplier);
    }

    public List<SupplierDTO> getAllSuppliers() {
        List<Supplier> suppliers = supplierDAO.findAll();
        List<SupplierDTO> supplierDTOs = new ArrayList<>();

        for (Supplier supplier : suppliers) {
            supplierDTOs.add(supplierMapper.toDto(supplier));
        }

        return supplierDTOs;
    }

    public SupplierDTO createSupplier(SupplierCreationDTO supplierCreationDTO) {

        if (supplierCreationDTO.getAddress() == null) {
            throw new AddressNotProvidedException("Address must be provided during registration.");
        }

        Address address = addressMapper.toEntity(supplierCreationDTO.getAddress());
        address = addressDAO.findByCountryAndCityAndStreetAndHouseAndApartment(address);

        Supplier supplier = supplierMapper.toEntity(supplierCreationDTO);
        supplier.setAddress_id(address.getId());
        Supplier savedSupplier = supplierDAO.save(supplier);
        return supplierMapper.toDto(savedSupplier);
    }

    public void deleteSupplier(long id) {
        Supplier supplier = supplierDAO.findById(id);
        if(supplier == null) {
            throw new SupplierNotFoundException("Supplier with id " + id + " not found.");
        }
        supplierDAO.deleteById(id);
    }

    public SupplierDTO updateSupplierAddress(Long id, AddressDTO addressDTO) {
        Supplier supplier = supplierDAO.findById(id);
        if(supplier == null) {
            throw new SupplierNotFoundException("Supplier with id " + id + " not found.");
        }
        Address address = addressMapper.toEntity(addressDTO);
        address = addressDAO.findByCountryAndCityAndStreetAndHouseAndApartment(address);
        supplier = supplierDAO.updateSupplierAddress(supplier.getId(), address.getId());
        return supplierMapper.toDto(supplier);
    }
}
