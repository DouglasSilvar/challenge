package com.challenge.api.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "planet")
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "planet_seq")
    @SequenceGenerator(name = "planet_seq", sequenceName = "planet_seq", initialValue = 1, allocationSize = 1)
    private Integer id;

    private String name;
    private String climate;
    private String terrain;

    @ManyToMany
    @JoinTable(
            name = "planet_film",
            joinColumns = @JoinColumn(name = "planet_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private List<Film> films;

}
