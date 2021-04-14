package app.dao;

import app.entity.CarEntity;

import java.util.List;
import java.util.Optional;

public interface CarDAO {

    Optional<CarEntity> getCar(Integer id);

    List<CarEntity> getCars();

    CarEntity saveCar(CarEntity car);

    void deleteCar(Integer id);
}
