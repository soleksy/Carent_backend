package app.service;

import app.dao.UserDAO;
import app.dto.PasswordChangeDto;
import app.dto.UserRegistrationDto;
import app.entity.UserEntity;
import app.entity.UserRoles;
import app.exception.ServerErrorCode;
import app.exception.ServerException;
import app.mapper.Mapper;
import app.utils.EncodingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Autowired
    private EncodingUtil encodingUtil;

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
    @Transactional(rollbackOn = {ServerException.class})
    public UserEntity enrollUser(UserRegistrationDto user) throws ServerException {
        validatePasswordsMatch(user.getPassword(), user.getConfirmPassword());
        UserEntity newUser = Mapper.map(user, UserEntity.class);
        newUser.setRoleId(UserRoles.CLIENT);
        newUser.setPassword(encodingUtil.encode(newUser.getPassword()));
        try {
            userDAO.createUser(newUser);
        } catch (DataIntegrityViolationException e) {
            String errorMsg = e.getMostSpecificCause().getMessage();
            if (errorMsg != null && errorMsg.contains("Duplicate entry")) {
                throw new ServerException(ServerErrorCode.EMAIL_NOT_UNIQUE, newUser.getEmail());
            }
            throw e;
        }
        return newUser;
    }

    private void validatePasswordsMatch(String password, String confirmPassword) throws ServerException {
        if (!password.equals(confirmPassword)) {
            throw new ServerException(ServerErrorCode.PASSWORDS_DONT_MATCH);
        }
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
    @Transactional //TODO invalidate active token
    public void changePassword(String username, PasswordChangeDto passwordChangeData) throws ServerException {
        validatePasswordsMatch(passwordChangeData.getNewPassword(), passwordChangeData.getConfirmPassword());
        UserEntity user = getUserByUsername(username);
        if (user == null || !encodingUtil.verify(user.getPassword(), passwordChangeData.getOldPassword())) {
            throw new ServerException(ServerErrorCode.INVALID_LOGIN_OR_PASSWORD);
        }
        user.setPassword(encodingUtil.encode(passwordChangeData.getNewPassword()));
        saveUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = self.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User wrong login or password");
        }
        return new User(user.getEmail(), user.getPassword(), user.getAuthorities());
    }
}
