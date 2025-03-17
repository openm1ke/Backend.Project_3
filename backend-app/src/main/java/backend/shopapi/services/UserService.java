package backend.shopapi.services;

import backend.shopapi.dao.AddressDAO;
import backend.shopapi.dao.UserDAO;
import backend.shopapi.dto.AddressDTO;
import backend.shopapi.dto.PaginatedResponse;
import backend.shopapi.dto.UserCreationDTO;
import backend.shopapi.dto.UserDTO;
import backend.shopapi.exceptions.AddressNotProvidedException;
import backend.shopapi.exceptions.UserNotFoundException;
import backend.shopapi.mappers.AddressMapper;
import backend.shopapi.mappers.UserMapper;
import backend.shopapi.models.Address;
import backend.shopapi.models.User;
import backend.shopapi.repository.AddressDAOImpl;
import backend.shopapi.repository.UserDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final AddressDAO addressDAO;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;

    @Autowired
    public UserService(UserDAOImpl userDAOimpl, AddressDAOImpl addressDAOimpl, UserMapper userMapper, AddressMapper addressMapper) {
        this.addressDAO = addressDAOimpl;
        this.userDAO = userDAOimpl;
        this.userMapper = userMapper;
        this.addressMapper = addressMapper;
    }

    public UserDTO getUserById(long id) {
        User user = userDAO.findById(id);
        if(user == null) {
            throw new UserNotFoundException("User with id " + id + " not found.");
        }
        return userMapper.toDto(user);
    }

    public List<UserDTO> getUserByClientNameAndSurname(String client_name, String client_surname) {
        List<User> users = userDAO.findByNameAndSurname(client_name, client_surname);
        List<UserDTO> userDTOs = new ArrayList<>();
        if(users == null) {
            throw new UserNotFoundException("User with name " + client_name + " and surname " + client_surname + " not found.");
        }
        for (User user : users) {
            userDTOs.add(userMapper.toDto(user));
        }
        return userDTOs;
    }

    public PaginatedResponse<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userDAO.findAll(pageable);
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : users.getContent()) {
            userDTOList.add(userMapper.toDto(user));
        }
        return new PaginatedResponse<>(userDTOList, pageable.getPageNumber(), pageable.getPageSize(), users.getTotalElements());
    }

    public UserDTO createUser(UserCreationDTO userCreationDTO) {

        if (userCreationDTO.getAddress() == null) {
            throw new AddressNotProvidedException("Address must be provided during registration.");
        }

        Address address = addressMapper.toEntity(userCreationDTO.getAddress());
        address = addressDAO.findByCountryAndCityAndStreetAndHouseAndApartment(address);
        User user = userMapper.toEntity(userCreationDTO);
        user.setAddress_id(address.getId());
        user = userDAO.save(user);
        return userMapper.toDto(user);
    }

    public void deleteUser(long id) {
        User user = userDAO.findById(id);
        if(user == null) {
            throw new UserNotFoundException("User with id " + id + " not found.");
        }
        userDAO.deleteById(id);
    }

    public UserDTO updateUserAddress(long id, AddressDTO addressDTO) {
        User user = userDAO.findById(id);
        if(user == null) {
            throw new UserNotFoundException("User with id " + id + " not found.");
        }
        Address address = addressMapper.toEntity(addressDTO);
        address = addressDAO.findByCountryAndCityAndStreetAndHouseAndApartment(address);
        user = userDAO.updateUserAddress(user.getId(), address.getId());
        return userMapper.toDto(user);
    }
}
