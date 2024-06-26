package me.kiryakov.animal_chips.dto;


import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kiryakov.animal_chips.domain.Gender;
import me.kiryakov.animal_chips.domain.LifeStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalSearchDTO {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDateTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDateTime;
    @Min(1)
    private Integer chipperId;
    @Min(1)
    private Long chippingLocationId;
    private LifeStatus lifeStatus;
    private Gender gender;
    @Min(0)
    private Integer from = 0;
    @Min(1)
    private Integer size = 10;
}
