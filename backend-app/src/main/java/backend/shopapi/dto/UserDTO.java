package backend.shopapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class UserDTO {
    private long id;
    private String login;
    private String email;
    private String client_name;
    private String client_surname;
    private String gender;
    private Date birthday;
    private LocalDateTime registration_date;
    private Long address_id;
}
