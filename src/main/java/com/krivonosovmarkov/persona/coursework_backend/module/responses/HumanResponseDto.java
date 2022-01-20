package com.krivonosovmarkov.persona.coursework_backend.module.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HumanResponseDto {

    private Long id;
    private String name;
    private String surname;
    private String locationName;
    private Boolean isReal;
    private Boolean isDoctor;
    private String sex;
    private String race;
    private String gender;
    private String temperament;
    private String status;
    private LocalDateTime birthDate;
    private LocalDateTime deathDate;

    private List<HumanEventResponseDto> humanEventResponseDtoList;
    private List<RelationshipResponseDto> relationshipResponseDtoList;
    private List<OpinionResponseDto> opinionResponseDtoList;

}
