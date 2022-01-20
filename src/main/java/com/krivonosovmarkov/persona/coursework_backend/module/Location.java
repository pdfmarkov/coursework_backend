package com.krivonosovmarkov.persona.coursework_backend.module;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="location")
public class Location {

    @Id
    @Column(name = "location_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long locationId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "real_location_id", referencedColumnName = "location_id")
    private Location realLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "world_id", referencedColumnName = "world_id")
    private World world;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Sight> sights;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Human> humans;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Shadow> shadows;

}
