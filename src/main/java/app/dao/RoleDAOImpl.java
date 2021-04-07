package app.dao;

import app.entity.RoleEntity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public RoleEntity getRole(int id) {
        Session session = sessionFactory.getCurrentSession();
        RoleEntity roleEntity = session.get(RoleEntity.class, id);
        return roleEntity;
    }

    @Override
    public List<RoleEntity> getRoles() {
        Session session = sessionFactory.getCurrentSession();
        List<RoleEntity> roles = session.createQuery("from role").list();
        return roles;
    }
}
