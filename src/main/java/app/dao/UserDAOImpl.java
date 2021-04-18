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
        return session.get(UserEntity.class, id);
    }

    @Override
    public List<UserEntity> getUsers() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from UserEntity", UserEntity.class).list();
    }

    @Override
    public void saveUser(UserEntity user) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteUser(UserEntity user) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(user);
    }

    @Override
    public UserEntity getUserByName(String username) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from UserEntity as item where item.firstName = :username", UserEntity.class)
                .setParameter("username", username).uniqueResult();
    }
}
