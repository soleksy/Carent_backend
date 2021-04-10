package app.dao;

import app.entity.RentalEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RentalDAOImpl implements RentalDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public RentalEntity getRental(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(RentalEntity.class, id);
    }

    @Override
    public List<RentalEntity> getRentals() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from RentalEntity ", RentalEntity.class).list();
    }

    @Override
    public void saveRental(RentalEntity rental) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(rental);
    }

    @Override
    public void deleteRental(RentalEntity rental) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(rental);
    }
}
