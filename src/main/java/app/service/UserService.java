package app.service;

import app.dto.PasswordChangeDto;
import app.dto.UserModificationDto;
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

    void changePassword(String token, String username, PasswordChangeDto passwordChangeData) throws ServerException;

    String modifyUser(String token, String username, UserModificationDto userModificationDto) throws ServerException;

    void logout(String token);
}
