package me.kiryakov.animal_chips.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "animals")
@SequenceGenerator(name = "AnimalsSequenceGenerator", sequenceName = "animals_seq", allocationSize = 1)
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AnimalSequenceGenerator")
    @Column(name = "id")
    private Long id;
    @Column(name = "weight")
    private Float weight;
    @Column(name = "length")
    private Float length;
    @Column(name = "height")
    private Float height;
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "life_status")
    private LifeStatus lifeStatus;
    @Column(name = "chipping_date_time")
    private LocalDateTime chippingDateTime;
    @Column(name = "death_date_time")
    private LocalDateTime deathDateTime;

    @ManyToOne
    @JoinColumn(name = "chipper_id")
    private Account chipper;

    @ManyToOne
    @JoinColumn(name = "chiping_location_id")
    private Location chippingLocation;

    @ManyToMany
    @JoinTable(name = "animal_type_rel",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_type_id"))
    private List<AnimalType> animalTypes = new ArrayList<>();

    @OneToMany(mappedBy = "animal", cascade = CascadeType.REMOVE)
    private List<VisitedLocation> visitedLocations = new ArrayList<>();
}
