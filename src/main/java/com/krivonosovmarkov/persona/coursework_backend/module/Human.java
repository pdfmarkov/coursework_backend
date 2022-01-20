package com.krivonosovmarkov.persona.coursework_backend.module;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name="human")
public class Human {

    public Human(Location location) {
        this.location = location;
        this.birthDate = LocalDateTime.now();
    }

    @Id
    @Column(name = "human_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long humanId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private Location location;

    @Column(name = "is_real")
    private Boolean isReal;

    @Column(name = "is_doctor")
    private Boolean isDoctor;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Enumerated(EnumType.STRING)
    @Column(name="sex")
    private Sex sex;

    @Column(name = "race")
    private String race;

    @Column(name = "gender")
    private String gender;

    @Enumerated(EnumType.STRING)
    @Column(name="temperament")
    private Temperament temperament;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Status status;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Column(name = "death_date")
    private LocalDateTime deathDate;

    @OneToMany(mappedBy = "human", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<HumanEvent> humanEventSet;

    @OneToMany(mappedBy = "human", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Opinion> opinions;

    @OneToMany(mappedBy = "human1", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Relationship> relationshipsFrom;

    @OneToMany(mappedBy = "human2", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Relationship> relationshipsTo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return humanId.equals(human.humanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(humanId);
    }

}
