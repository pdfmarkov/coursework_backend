package com.krivonosovmarkov.persona.coursework_backend.module;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="relationship")
public class Relationship {

    @EmbeddedId
    private RelationshipId relationshipId;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("human1Id")
    private Human human1;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("human2Id")
    private Human human2;

    @Enumerated(EnumType.STRING)
    @Column(name="type")
    private TypeOfRelations type;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

}
