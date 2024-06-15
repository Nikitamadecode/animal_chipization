package me.kiryakov.animal_chips.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
