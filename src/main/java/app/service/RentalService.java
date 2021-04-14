package app.service;

import app.dto.RentalRequestDto;
import app.dto.RentalResponseDto;

import java.util.List;

public interface RentalService {

    RentalResponseDto getRental(Integer id);

    List<RentalResponseDto> getRentals();

    RentalResponseDto saveRental(RentalRequestDto rental);

    void deleteRental(Integer id);

    RentalResponseDto updateRental(Integer id, RentalRequestDto car);
}
