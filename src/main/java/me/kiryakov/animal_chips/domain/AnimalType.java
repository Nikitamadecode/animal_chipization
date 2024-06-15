package me.kiryakov.animal_chips.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "animal_type")
@SequenceGenerator(name = "AnimalTypeSequenceGenerator", sequenceName = "animal_type_seq", allocationSize = 1)
public class AnimalType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AnimalTypeSequenceGenerator")
    @Column(name = "id")
    private Long id;
    @Column(name = "type")
    private String type;

    @ManyToMany
    @JoinTable(name = "animal_type_rel",
            joinColumns = @JoinColumn(name = "animal_type_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_id"))
    private List<Animal> animals;
}
