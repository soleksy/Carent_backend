package app.controller;

import app.dto.CarRequestDto;
import app.dto.CarResponseDto;
import app.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
@CrossOrigin
public class CarController {
    private final CarService carService;

    @GetMapping
    public List<CarResponseDto> getCars() {
        return carService.getCars();
    }

    @GetMapping("/{id}")
    public CarResponseDto getCar(@PathVariable Integer id) {
        return carService.getCar(id);
    }

    @PostMapping
    public CarResponseDto addCar(@RequestBody @Valid CarRequestDto car) {
        return carService.saveCar(car);
    }

    @PutMapping("{id}")
    void updateCar(@PathVariable @NotNull Integer id, @RequestBody @Valid CarRequestDto car) {
        carService.updateCar(id, car);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCar(@PathVariable @NotNull Integer id) {
        carService.deleteCar(id);
    }
}
