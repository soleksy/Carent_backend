package app.service;

import app.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    UserEntity getUser(int id);

    List<UserEntity> getUsers();

    void saveUser(UserEntity user);

    void deleteUser(UserEntity user);

    UserEntity getUserByUsername(String username);
}
