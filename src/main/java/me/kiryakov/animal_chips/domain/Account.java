package me.kiryakov.animal_chips.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
@SequenceGenerator(name = "AccountSequenceGenerator", sequenceName = "account_seq", allocationSize = 1)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AccountSequenceGenerator")
    @Column(name = "id")
    private Integer id;
    @Column(name = "first_name", length = 50)
    private String firstName;
    @Column(name = "last_name", length = 50 )
    private String lastName;
    @Column(name = "email", length = 50)
    private String email;
    @Column(name = "password")
    private String password;

}
