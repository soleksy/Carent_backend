package app.service;

import app.dto.PasswordChangeDto;
import app.dto.UserRegistrationDto;
import app.entity.UserEntity;
import app.exception.ServerException;

import java.util.List;

public interface UserService {

    UserEntity getUser(int id);

    List<UserEntity> getUsers();

    void saveUser(UserEntity user);

    UserEntity enrollUser(UserRegistrationDto user) throws ServerException;

    void deleteUser(UserEntity user);

    UserEntity getUserByUsername(String username);

    void changePassword(String username, PasswordChangeDto passwordChangeData) throws ServerException;
}
