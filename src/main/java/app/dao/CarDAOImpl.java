package app.dao;

import app.entity.CarEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarDAOImpl implements CarDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public CarEntity getCar(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        CarEntity carEntity = session.get(CarEntity.class, id);
        return carEntity;
    }

    @Override
    public List<CarEntity> getCars() {
        Session session = sessionFactory.getCurrentSession();
        List<CarEntity> cars = session.createQuery("from car").list();
        return cars;
    }
}
