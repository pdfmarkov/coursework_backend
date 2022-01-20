package com.krivonosovmarkov.persona.coursework_backend.module;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="sight")
public class Sight {

    @Id
    @Column(name = "sight_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sightId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "real_sight_id", referencedColumnName = "sight_id")
    private Sight realSight;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private Location location;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "construction_date")
    private LocalDateTime constructionDate;

}
