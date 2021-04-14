package app.dao;

import app.entity.RentalEntity;

import java.util.List;
import java.util.Optional;

public interface RentalDAO {

    Optional<RentalEntity> getRental(int id);

    List<RentalEntity> getRentals();

    RentalEntity saveRental(RentalEntity rentalEntity);

    void deleteRental(Integer id);
}
