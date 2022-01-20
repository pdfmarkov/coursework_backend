package com.krivonosovmarkov.persona.coursework_backend.module;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="opinion")
public class Opinion {

    @EmbeddedId
    private OpinionId opinionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("humanId")
    private Human human;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("propertyId")
    private Property property;

    @Column(name = "is_good")
    private Boolean isGood;

}
