package app.service;

import app.dao.UserDAO;
import app.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserSerivceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserService self;

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

    @Override
    @Transactional
    public UserEntity getUserByUsername(String username) {
        return userDAO.getUserByName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = self.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User wrong login or password");
        }
        return new User(user.getFirstName(), user.getPassword(), user.getAuthorities());
    }
}
