package me.kiryakov.animal_chips.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditAnimalTypeRequestDTO {
    @Min(1)
    @NotNull
    private Long oldTypeId;
    @Min(1)
    @NotNull
    private Long newTypeId;
}
