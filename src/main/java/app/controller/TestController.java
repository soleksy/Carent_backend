package app.controller;

import app.entity.CarEntity;
import app.entity.RentalEntity;
import app.entity.UserEntity;
import app.security.filters.TokenFactory;
import app.service.CarService;
import app.service.RentalService;
import app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/test")
@CrossOrigin // TODO add cors filter
public class TestController {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private CarService carService;
    @Autowired
    private RentalService rentalService;
    @Autowired
    private TokenFactory tokenFactory;

    @PreAuthorize("hasRole('client')")
    @RequestMapping("/getUser")
    public UserEntity getUser(){
        UserEntity userEntity = userService.getUser(1);
        log.info("User: " +userEntity.getFirstName() + " " + userEntity.getSecondName());
        return userEntity;
    }

    @PreAuthorize("hasAnyRole('admin', 'rootAdmin')")
    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public List<UserEntity> getUsers(){
        return  userService.getUsers();
    }

    @RequestMapping("/getCar")
    public CarEntity getCar(){
        return carService.getCar(1);
    }

    @RequestMapping("/getCars")
    public List<CarEntity> getCars(){
        return carService.getCars();
    }


    @RequestMapping("/setRental")
    public UserEntity setRental(){
        UserEntity userEntity = userService.getUser(1);
        CarEntity car = carService.getCar(1);
        RentalEntity rentalEntity = new RentalEntity();
        rentalEntity.setCar(car);
        rentalEntity.setUser(userEntity);
        rentalEntity.setStartDate(new Date(2021, Calendar.NOVEMBER,10));
        rentalEntity.setEndDate(new Date(2021, Calendar.DECEMBER,20));
        rentalService.saveRental(rentalEntity);
        return userEntity;
    }

    @RequestMapping("/getRental")
    public RentalEntity getRental(){
        RentalEntity rentalEntity = rentalService.getRental(1);
        return rentalEntity;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String login(HttpServletRequest request){
        return tokenFactory.getAccessToken(request.getHeader("Authorization"));
    }
}
