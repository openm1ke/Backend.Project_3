package backend.shopapi.mappers;

import backend.shopapi.dto.UserCreationDTO;
import backend.shopapi.dto.UserDTO;
import backend.shopapi.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setEmail(user.getEmail());
        userDTO.setClient_name(user.getClient_name());
        userDTO.setClient_surname(user.getClient_surname());
        userDTO.setGender(user.getGender());
        userDTO.setBirthday(user.getBirthday());
        userDTO.setRegistration_date(user.getRegistration_date());
        userDTO.setAddress_id(user.getAddress_id());
        return userDTO;
    }

    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setLogin(userDTO.getLogin());
        user.setEmail(userDTO.getEmail());
        user.setClient_name(userDTO.getClient_name());
        user.setClient_surname(userDTO.getClient_surname());
        user.setGender(userDTO.getGender());
        user.setBirthday(userDTO.getBirthday());
        user.setRegistration_date(userDTO.getRegistration_date());
        user.setAddress_id(userDTO.getAddress_id());
        return user;
    }

    public User toEntity(UserCreationDTO userCreationDTO) {
        User user = new User();
        user.setLogin(userCreationDTO.getLogin());
        user.setEmail(userCreationDTO.getEmail());
        user.setPassword(userCreationDTO.getPassword());
        user.setClient_name(userCreationDTO.getClient_name());
        user.setClient_surname(userCreationDTO.getClient_surname());
        user.setGender(userCreationDTO.getGender());
        user.setBirthday(userCreationDTO.getBirthday());
        return user;
    }
}
