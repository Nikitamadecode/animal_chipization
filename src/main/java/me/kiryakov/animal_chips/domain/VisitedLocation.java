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
@Table(name = "visited_location")
@SequenceGenerator(name = "VisitedLocationSequenceGenerator", sequenceName = "visited_loc_seq", allocationSize = 1)
public class VisitedLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VisitedLocationSequenceGenerator")
    @Column(name = "id")
    private Long id;
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
}
