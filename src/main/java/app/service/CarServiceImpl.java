package app.service;

import app.dao.CarDAO;
import app.dto.CarRequestDto;
import app.dto.CarResponseDto;
import app.entity.CarEntity;
import app.exception.EntityNotFoundException;
import app.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarDAO carDAO;

    @Override
    @Transactional
    public CarResponseDto getCar(Integer id) {
        Objects.requireNonNull(id);
        CarEntity carEntity = carDAO.getCar(id).orElseThrow(() -> new EntityNotFoundException(CarEntity.class.getSimpleName(), id));
        return Mapper.map(carEntity, CarResponseDto.class);
    }

    @Override
    @Transactional
    public List<CarResponseDto> getCars() {
        return Mapper.mapList(carDAO.getCars(), CarResponseDto.class);
    }

    @Override
    @Transactional
    public CarResponseDto saveCar(CarRequestDto car) {
        Objects.requireNonNull(car);
        CarEntity carEntity = Mapper.map(car, CarEntity.class);
        return Mapper.map(carDAO.saveCar(carEntity), CarResponseDto.class);
    }

    @Override
    @Transactional
    public void deleteCar(Integer id) {
        Objects.requireNonNull(id);
        carDAO.deleteCar(id);
    }

    @Override
    public void updateCar(Integer id, CarRequestDto car) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(car);
        CarEntity carEntity = Mapper.map(car, CarEntity.class);
        carEntity.setId(id);
        carDAO.saveCar(carEntity);
    }
}
