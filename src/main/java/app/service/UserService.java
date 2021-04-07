package app.service;

import app.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity getUser(int id);

    List<UserEntity> getUsers();
}
