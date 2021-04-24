package app.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class UserRegistrationDto {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    @Email(regexp = "^\\w+(\\.\\w+)*@(\\w+\\.)*\\w+$")
    private String email;
    @NotNull
    private Date dateOfBirth;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;
}
