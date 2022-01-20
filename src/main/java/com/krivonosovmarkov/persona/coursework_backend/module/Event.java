package com.krivonosovmarkov.persona.coursework_backend.module;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="event")
public class Event {

    public Event(Long eventId, String name, String description, LocalDateTime date) {
        this.eventId = eventId;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long eventId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private Location location;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private LocalDateTime date;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<HumanEvent> humanEventSet;

    public Event() {

    }
}
