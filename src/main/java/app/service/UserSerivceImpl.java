package app.service;

import app.dao.UserDAO;
import app.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserSerivceImpl implements UserService{

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public UserEntity getUser(int id) {
        return userDAO.getUser(id);
    }

    @Override
    @Transactional
    public List<UserEntity> getUsers() {
        return userDAO.getUsers();
    }

    @Override
    @Transactional
    public void saveUser(UserEntity user) {
        userDAO.saveUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(UserEntity user) {
        userDAO.deleteUser(user);
    }
}
