package com.krivonosovmarkov.persona.coursework_backend.module;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class RelationshipId implements Serializable {

    @Column(name = "human1_human_id")
    private Long human1Id;

    @Column(name = "human2_human_id")
    private Long human2Id;

}
