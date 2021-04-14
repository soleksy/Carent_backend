package app.dto;

import app.entity.Gearbox;
import lombok.Data;

@Data
public class CarResponseDto {
    private Integer id;
    private String brand;
    private String model;
    private Gearbox gearbox;
    private Integer pricePerDay;
    private Integer amountOfDoors;
    private Integer amountOfSeats;
    private Integer mileageInLiter;
    private boolean airConditioning;
}
