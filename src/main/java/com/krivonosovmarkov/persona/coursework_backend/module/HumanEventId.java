package com.krivonosovmarkov.persona.coursework_backend.module;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class HumanEventId implements Serializable {

    @Column(name = "human_human_id")
    private Long humanId;

    @Column(name = "event_event_id")
    private Long eventId;

}
