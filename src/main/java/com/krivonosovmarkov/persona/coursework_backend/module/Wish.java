package com.krivonosovmarkov.persona.coursework_backend.module;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="wish")
public class Wish {

    @Id
    @Column(name = "wish_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long wishId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shadow_id", referencedColumnName = "shadow_id")
    private Shadow shadow;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wish_human_id", referencedColumnName = "human_id")
    private Human wishHuman;

    @Enumerated(EnumType.STRING)
    @Column(name="type")
    private TypeOfWish type;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "real_other_human_id", referencedColumnName = "human_id")
    private Human realOtherHuman;

}
