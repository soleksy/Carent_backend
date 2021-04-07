package app.service;

import app.entity.CarEntity;
import app.entity.UserEntity;

import java.util.List;

public interface CarService {

    CarEntity getCar(Integer id);

    List<CarEntity> getCars();
}
