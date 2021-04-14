package app.dto;

import app.entity.CarEntity;
import app.entity.UserEntity;
import lombok.Data;

import java.util.Date;

@Data
public class RentalResponseDto {
    private Integer id;
    private UserEntity user;
    private CarEntity car;
    private Date startDate;
    private Date endDate;
}
