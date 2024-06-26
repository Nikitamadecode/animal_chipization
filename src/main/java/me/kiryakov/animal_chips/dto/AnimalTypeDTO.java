package me.kiryakov.animal_chips.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalTypeDTO {
    private Long id;
    @NotNull
    @NotEmpty
    @NotBlank
    private String type;
}
