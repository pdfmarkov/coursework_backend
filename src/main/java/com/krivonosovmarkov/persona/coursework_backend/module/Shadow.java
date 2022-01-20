package com.krivonosovmarkov.persona.coursework_backend.module;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="shadow")
public class Shadow {

    @Id
    @Column(name = "shadow_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shadowId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private Location location;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "real_human_id", referencedColumnName = "human_id")
    private Human human;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "shadow", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Wish> wishes;

}
