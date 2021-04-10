package app.dao;

import app.entity.CarEntity;

import java.util.List;

public interface CarDAO {

    CarEntity getCar(Integer id);

    List<CarEntity> getCars();

    void saveCar(CarEntity car);

    void deleteCar(CarEntity car);
}
