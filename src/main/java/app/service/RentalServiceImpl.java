package app.service;

import app.dao.CarDAO;
import app.dao.RentalDAO;
import app.dao.UserDAO;
import app.dto.RentalRequestDto;
import app.dto.RentalResponseDto;
import app.entity.CarEntity;
import app.entity.RentalEntity;
import app.entity.UserEntity;
import app.exception.EntityNotFoundException;
import app.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalDAO rentalDAO;
    private final UserDAO userDAO;
    private final CarDAO carDAO;

    @Override
    @Transactional
    public RentalResponseDto getRental(Integer id) {
        Objects.requireNonNull(id);
        RentalEntity rentalEntity = rentalDAO.getRental(id).orElseThrow(() -> new EntityNotFoundException(RentalEntity.class.getSimpleName(), id));
        return Mapper.map(rentalEntity, RentalResponseDto.class);
    }

    @Override
    @Transactional
    public List<RentalResponseDto> getRentals() {
        return Mapper.mapList(rentalDAO.getRentals(), RentalResponseDto.class);
    }

    @Override
    @Transactional
    public RentalResponseDto saveRental(RentalRequestDto rental) {
        Objects.requireNonNull(rental);
        CarEntity car = carDAO.getCar(rental.getCarId()).orElseThrow(
                () -> new EntityNotFoundException(CarEntity.class.getSimpleName(), rental.getCarId()));
        UserEntity user = userDAO.getUser(rental.getUserId());
        RentalEntity entity = RentalEntity.builder()
                .car(car)
                .user(user)
                .startDate(rental.getStartDate())
                .endDate(rental.getEndDate())
                .build();
        return Mapper.map(rentalDAO.saveRental(entity), RentalResponseDto.class);
    }

    @Override
    @Transactional
    public void deleteRental(Integer id) {
        Objects.requireNonNull(id);
        rentalDAO.deleteRental(id);
    }

    @Override
    public RentalResponseDto updateRental(Integer id, RentalRequestDto rental) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(rental);
        CarEntity car = carDAO.getCar(rental.getCarId()).orElseThrow(
                () -> new EntityNotFoundException(CarEntity.class.getSimpleName(), rental.getCarId()));
        UserEntity user = userDAO.getUser(rental.getUserId());
        RentalEntity entity = RentalEntity.builder()
                .id(id)
                .car(car)
                .user(user)
                .startDate(rental.getStartDate())
                .endDate(rental.getEndDate())
                .build();
        return Mapper.map(rentalDAO.saveRental(entity), RentalResponseDto.class);
    }
}