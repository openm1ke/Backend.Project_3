package backend.shopapi.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class UserResponseDTO {
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
