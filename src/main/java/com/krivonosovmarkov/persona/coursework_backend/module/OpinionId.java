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
public class OpinionId implements Serializable {

    @Column(name = "human_human_id")
    private Long humanId;

    @Column(name = "property_property_id")
    private Long propertyId;

}
