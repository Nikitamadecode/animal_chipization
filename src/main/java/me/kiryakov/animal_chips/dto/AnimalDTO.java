package me.kiryakov.animal_chips.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kiryakov.animal_chips.domain.Account;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDTO {
    private Long id;
    private Float weight;
    private Float length;
    private Float height;
    private String gender;
    private String lifeStatus;
    private LocalDateTime chippingDateTime;
    private LocalDateTime deathDateTime;
    private Integer chipperId;
    private Long chippingLocationId;
    private List<Long> animalTypes;
}


