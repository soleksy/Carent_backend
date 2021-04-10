package app.service;

import app.entity.CarEntity;
import java.util.List;

public interface CarService {

    CarEntity getCar(Integer id);

    List<CarEntity> getCars();

    void saveUser(CarEntity car);

    void deleteUser(CarEntity car);
}
