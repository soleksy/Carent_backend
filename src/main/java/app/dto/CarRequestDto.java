package app.dto;

import app.entity.Gearbox;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CarRequestDto {
    @NotEmpty
    private String brand;
    @NotEmpty
    private String model;
    @NotNull
    private Gearbox gearbox;
    @NotNull
    @Min(1)
    private Integer pricePerDay;
    @NotNull
    @Min(3)
    private Integer amountOfDoors;
    @NotNull
    @Min(2)
    private Integer amountOfSeats;
    private Integer mileageInLiter;
    private boolean airConditioning;
}
