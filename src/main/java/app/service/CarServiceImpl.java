package app.service;

import app.dao.CarDAO;
import app.entity.CarEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CarServiceImpl implements CarService{

    @Autowired
    private CarDAO carDAO;

    @Override
    @Transactional
    public CarEntity getCar(Integer id) {
        return carDAO.getCar(id);
    }

    @Override
    @Transactional
    public List<CarEntity> getCars() {
        return carDAO.getCars();
    }

    @Override
    @Transactional
    public void saveUser(CarEntity car) {
        carDAO.saveCar(car);
    }

    @Override
    @Transactional
    public void deleteUser(CarEntity car) {
        carDAO.deleteCar(car);
    }
}
