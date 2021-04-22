package app.dao;

import app.entity.CarEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CarDAOImpl implements CarDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<CarEntity> getCar(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(CarEntity.class, id));
    }

    @Override
    public List<CarEntity> getCars() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from CarEntity ", CarEntity.class).list();
    }

    @Override
    public CarEntity saveCar(CarEntity car) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(car);
        return car;
    }

    @Override
    public void deleteCar(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        CarEntity carEntity = session.get(CarEntity.class, id);
        session.delete(carEntity);
    }
}
