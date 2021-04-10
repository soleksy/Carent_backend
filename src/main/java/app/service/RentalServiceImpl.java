package app.service;

import app.dao.RentalDAO;
import app.entity.RentalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RentalServiceImpl implements RentalService{

    @Autowired
    private RentalDAO rentalDAO;

    @Override
    @Transactional
    public RentalEntity getRental(int id) {
        return rentalDAO.getRental(id);
    }

    @Override
    @Transactional
    public List<RentalEntity> getRentals() {
        return rentalDAO.getRentals();
    }

    @Override
    @Transactional
    public void saveRental(RentalEntity rentalEntity) {
        rentalDAO.saveRental(rentalEntity);
    }

    @Override
    @Transactional
    public void deleteRental(RentalEntity rentalEntity) {
        rentalDAO.deleteRental(rentalEntity);
    }
}
