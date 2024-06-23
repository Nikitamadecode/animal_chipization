package me.kiryakov.animal_chips.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kiryakov.animal_chips.domain.Account;
import me.kiryakov.animal_chips.domain.Gender;
import me.kiryakov.animal_chips.domain.LifeStatus;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnimalDTO {
    private Long id;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private Float weight;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private Float length;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private Float height;
    @NotNull
    private Gender gender;
    private LifeStatus lifeStatus;
    private LocalDateTime chippingDateTime;
    private LocalDateTime deathDateTime;
    @Min(1)
    @NotNull
    private Integer chipperId;
    @Min(1)
    @NotNull
    private Long chippingLocationId;
    private List<@Min(1) Long> animalTypes;
    private List<Long> visitedLocations;
}


