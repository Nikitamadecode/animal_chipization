package me.kiryakov.animal_chips.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "animal_location")
@SequenceGenerator(name = "AnimalLocationSequenceGenerator", sequenceName = "animal_location_seq", allocationSize = 1)
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AnimalLocationSequenceGenerator")
    @Column(name = "id")
    private Long id;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longtitude")
    private Double longitude;
    @OneToMany(mappedBy = "location")
    private List<VisitedLocation> visitedLocations = new ArrayList<>();
    @OneToMany(mappedBy = "chippingLocation")
    private List<Animal> animals = new ArrayList<>();


}

