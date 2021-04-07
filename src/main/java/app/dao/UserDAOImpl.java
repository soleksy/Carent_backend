package app.dao;

import app.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public UserEntity getUser(int id) {
        Session session = sessionFactory.getCurrentSession();
        UserEntity userEntity = session.get(UserEntity.class, id);
        return userEntity;
    }

    @Override
    public List<UserEntity> getUsers() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from user", UserEntity.class).list();
    }
}
