package app.controller;

import app.dto.UserRegistrationDto;
import app.entity.UserEntity;
import app.exception.ServerException;
import app.security.filters.TokenFactory;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    @Autowired
    private TokenFactory tokenFactory;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/auth")
    public String login(HttpServletRequest request) throws ServerException {
        return tokenFactory.getAccessToken(request.getHeader("Authorization"));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserEntity register(@RequestBody @Valid UserRegistrationDto newUser) throws ServerException {
        return userService.enrollUser(newUser);
    }

}
