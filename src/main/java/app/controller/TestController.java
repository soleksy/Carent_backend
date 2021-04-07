package app.controller;

import app.entity.CarEntity;
import app.entity.RoleEntity;
import app.entity.UserEntity;
import app.service.CarService;
import app.service.RoleService;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;
    @Autowired
    private CarService carService;
    @Autowired
    private RoleService roleService;


    @RequestMapping("/getUser")
    public UserEntity getUser(){
        UserEntity userEntity = userService.getUser(1);
        return userEntity;
    }

    @RequestMapping("/getUsers")
    public List<UserEntity> getUsers(){
        return  userService.getUsers();
//        return users;
    }

    @RequestMapping("/getCar")
    public CarEntity getCar(){
       CarEntity car = carService.getCar(1);
        return car;
    }

    @RequestMapping("/getCars")
    public List<CarEntity> getCars(){
        List<CarEntity> cars = carService.getCars();
        return cars;
    }

    @RequestMapping("/getRole")
    public RoleEntity getRole(){
        RoleEntity role = roleService.getRole(1);
        return role;
    }

    @RequestMapping("/getRoles")
    public List<RoleEntity> getRoles(){
        List<RoleEntity> roles = roleService.getRoles();
        return roles;
    }
}
