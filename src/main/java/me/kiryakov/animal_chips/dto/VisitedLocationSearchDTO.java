package me.kiryakov.animal_chips.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitedLocationSearchDTO {
    @Min(1)
    private Long animalId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @Min(0)
    private Integer from = 0;
    @Min(1)
    private Integer size = 10;
}
