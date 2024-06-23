package me.kiryakov.animal_chips.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
    private Long id;
    @Min(-90)
    @Max(90)
    @NotNull
    private Double latitude;
    @Min(-180)
    @Max(180)
    @NotNull
    private Double longitude;
}
