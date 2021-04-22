package app.service;

import app.dao.RentalDAO;
import app.dto.RentalRequestDto;
import app.dto.RentalResponseDto;
import app.entity.RentalEntity;
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
        RentalEntity rentalEntity = Mapper.map(rental, RentalEntity.class);
        return Mapper.map(rentalDAO.saveRental(rentalEntity), RentalResponseDto.class);
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
        RentalEntity rentalEntity = Mapper.map(id, RentalEntity.class);
        rentalEntity.setId(id);
        return Mapper.map(rentalDAO.saveRental(rentalEntity), RentalResponseDto.class);
    }
}