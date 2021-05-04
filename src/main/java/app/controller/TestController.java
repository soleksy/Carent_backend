package app.controller;

import app.entity.UserEntity;
import app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('client')")
    @RequestMapping("/getUser")
    public UserEntity getUser() {
        UserEntity userEntity = userService.getUser(1);
        log.info("User: " + userEntity.getFirstName() + " " + userEntity.getLastName());
        return userEntity;
    }

    @PreAuthorize("hasAnyRole('admin', 'rootAdmin')")
    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public List<UserEntity> getUsers(){
        return  userService.getUsers();
    }
}
