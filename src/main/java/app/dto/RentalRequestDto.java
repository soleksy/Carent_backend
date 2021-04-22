package app.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class RentalRequestDto {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer carId;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
}
