package me.kiryakov.animal_chips.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class VisitedLocationDTO {
    private Long id;
    private LocalDateTime dateTimeOfVisitLocationPoint;
    @Min(0)
    private Long locationPointId;
}
