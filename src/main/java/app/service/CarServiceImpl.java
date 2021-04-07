package app.service;

import app.dao.CarDAO;
import app.entity.CarEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService{

    @Autowired
    private CarDAO carDAO;

    @Override
    public CarEntity getCar(Integer id) {
        return carDAO.getCar(id);
    }

    @Override
    public List<CarEntity> getCars() {
        return carDAO.getCars();
    }
}
