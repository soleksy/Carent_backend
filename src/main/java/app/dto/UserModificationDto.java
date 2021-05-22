package app.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.util.Date;

@Getter
@Setter
public class UserModificationDto {

    private String firstName;
    private String lastName;
    @Email(regexp = "^\\w+(\\.\\w+)*@(\\w+\\.)*\\w+$")
    private String email;
    private Date dateOfBirth;

}
