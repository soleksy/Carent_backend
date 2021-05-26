package app.dao;

import app.entity.RentalEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RentalDAOImpl implements RentalDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<RentalEntity> getRental(int id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(RentalEntity.class, id));
    }

    @Override
    public List<RentalEntity> getRentals() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from RentalEntity ", RentalEntity.class).list();
    }

    @Override
    public RentalEntity saveRental(RentalEntity rental) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(rental);
        return rental;
    }

    @Override
    public void deleteRental(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        RentalEntity rentalEntity = session.get(RentalEntity.class, id);
        session.remove(rentalEntity);
    }

    @Override
    public List<RentalEntity> getUserRentals(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from RentalEntity as item where item.user.id = :userId", RentalEntity.class)
                .setParameter("userId", userId).getResultList();
    }
}
