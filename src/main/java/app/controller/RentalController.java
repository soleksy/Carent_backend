package app.controller;

import app.dto.RentalRequestDto;
import app.dto.RentalResponseDto;
import app.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/rental")
@RequiredArgsConstructor
@CrossOrigin
public class RentalController {
    private final RentalService rentalService;

    @GetMapping
    public List<RentalResponseDto> getRentals() {
        return rentalService.getRentals();
    }

    @GetMapping("/{id}")
    public RentalResponseDto getRental(@PathVariable Integer id) {
        return rentalService.getRental(id);
    }

    @PostMapping
    public RentalResponseDto addRental(@RequestBody @Valid RentalRequestDto car) {
        return rentalService.saveRental(car);
    }

    @PutMapping("{id}")
    void updateRental(@PathVariable @NotNull Integer id, @RequestBody @Valid RentalRequestDto car) {
        rentalService.updateRental(id, car);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRental(@PathVariable @NotNull Integer id) {
        rentalService.deleteRental(id);
    }
}
