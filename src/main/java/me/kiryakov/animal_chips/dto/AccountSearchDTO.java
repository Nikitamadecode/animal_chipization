package me.kiryakov.animal_chips.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class AccountSearchDTO {
    private String firstName;
    private String lastName;
    private String email;
    @Min(0)
    private Integer from = 0;
    @Min(1)
    private Integer size = 10;
}
