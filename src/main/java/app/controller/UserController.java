package app.controller;

import app.dto.*;
import app.entity.RentalEntity;
import app.entity.UserEntity;
import app.exception.ServerErrorCode;
import app.exception.ServerException;
import app.security.AuthorizedAs;
import app.security.filters.TokenFactory;
import app.service.RentalService;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private TokenFactory tokenFactory;

    @Autowired
    private UserService userService;

    @Autowired
    private RentalService rentalService;

    @AuthorizedAs({"rootAdmin", "admin"})
    @GetMapping(value = "/{id}")
    public UserEntity getUserById(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @GetMapping(value = "/self")
    public UserEntity getUser(Authentication authentication) throws ServerException {
        if (authentication == null) {
            throw new ServerException(ServerErrorCode.INVALID_ACCESS_TOKEN);
        }
        return userService.getUserByUsername(authentication.getName());
    }

    @PutMapping(value = "/self/change-password")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void changePassword(HttpServletRequest request, Authentication authentication, @Valid @RequestBody PasswordChangeDto passwordChangeData) throws ServerException {
        if (authentication == null) {
            throw new ServerException(ServerErrorCode.INVALID_ACCESS_TOKEN);
        }
        String token = tokenFactory.getBearer(request.getHeader("Authorization"));
        userService.changePassword(token, authentication.getName(), passwordChangeData);
    }

    @PutMapping(value = "/self/modify")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String modifyUser(HttpServletRequest request, Authentication authentication, @Valid @RequestBody UserModificationDto modificationDto) throws ServerException {
        if (authentication == null) {
            throw new ServerException(ServerErrorCode.INVALID_ACCESS_TOKEN);
        }
        String token = tokenFactory.getBearer(request.getHeader("Authorization"));
        return userService.modifyUser(token, authentication.getName(), modificationDto);
    }

    @PostMapping(value = "/self/logout")
    public void logout(HttpServletRequest request, Authentication authentication) throws ServerException {
        if (authentication == null) {
            throw new ServerException(ServerErrorCode.INVALID_ACCESS_TOKEN);
        }
        String token = tokenFactory.getBearer(request.getHeader("Authorization"));
        userService.logout(token);
    }

    @PostMapping(value = "/auth")
    public String login(HttpServletRequest request) throws ServerException {
        return tokenFactory.getAccessToken(request.getHeader("Authorization"));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserEntity register(@RequestBody @Valid UserRegistrationDto newUser) throws ServerException {
        return userService.enrollUser(newUser);
    }

    @GetMapping("/{userId}/rentals")
    public List<RentalEntity> getUserRental(@PathVariable @NotNull Integer userId) {
        return rentalService.getUserRentals(userId);
    }

}
