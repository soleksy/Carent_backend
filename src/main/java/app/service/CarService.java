package app.service;

import app.dto.CarRequestDto;
import app.dto.CarResponseDto;

import java.util.List;

public interface CarService {

    CarResponseDto getCar(Integer id);

    List<CarResponseDto> getCars();

    CarResponseDto saveCar(CarRequestDto car);

    void updateCar(Integer id, CarRequestDto car);

    void deleteCar(Integer id);
}
