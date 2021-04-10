package app.service;

import app.entity.RentalEntity;

import java.util.List;

public interface RentalService {

    RentalEntity getRental(int id);

    List<RentalEntity> getRentals();

    void saveRental(RentalEntity rentalEntity);

    void deleteRental(RentalEntity rentalEntity);
}
