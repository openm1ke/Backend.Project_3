package backend.shopapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserCreationDTO {
    @NotBlank(message = "Login must not be blank")
    @Size(min = 3, max = 20, message = "Login must be between 3 and 20 characters")
    private String login;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email must not be blank")
    private String email;
    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    private String client_name;
    private String client_surname;
    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @Valid // Проверка вложенных полей внутри address
    private AddressDTO address;
}

