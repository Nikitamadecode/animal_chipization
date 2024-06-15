package me.kiryakov.animal_chips.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalTypeDTO {
    private Long id;
    private String type;
}
