package com.krivonosovmarkov.persona.coursework_backend.module;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name="humanevent")
public class HumanEvent {

    @EmbeddedId
    private HumanEventId humanEventId;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("humanId")
    private Human human;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("eventId")
    private Event event;

    @Column(name = "is_good")
    private Boolean isGood;

}
