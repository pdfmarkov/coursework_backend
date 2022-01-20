package com.krivonosovmarkov.persona.coursework_backend.module;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="property")
public class Property {

    @Id
    @Column(name = "property_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long propertyId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Opinion> opinions;

}
