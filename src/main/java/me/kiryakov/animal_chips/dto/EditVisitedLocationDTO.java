package me.kiryakov.animal_chips.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditVisitedLocationDTO {
    @Min(1)
    private Long visitedLocationPointId;
    @Min(1)
    private Long locationPointId;
}
