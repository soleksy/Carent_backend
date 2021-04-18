package app.dao;

import app.entity.UserEntity;
import java.util.List;

public interface UserDAO {

    UserEntity getUser(int id);

    List<UserEntity> getUsers();

    void saveUser(UserEntity user);

    void deleteUser(UserEntity user);

    UserEntity getUserByName(String username);
}
