package com.krivonosovmarkov.persona.coursework_backend.module;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="world")
public class World {

    @Id
    @Column(name = "world_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long worldId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "real_world_id", referencedColumnName = "world_id")
    private World realWorld;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "world", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Location> locations;

}
