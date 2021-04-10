package app.dao;

import app.entity.RentalEntity;

import java.util.List;

public interface RentalDAO {

    RentalEntity getRental(int id);

    List<RentalEntity> getRentals();

    void saveRental(RentalEntity rentalEntity);

    void deleteRental(RentalEntity rentalEntity);
}
