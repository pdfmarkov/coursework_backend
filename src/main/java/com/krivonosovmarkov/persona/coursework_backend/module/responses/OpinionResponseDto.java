package com.krivonosovmarkov.persona.coursework_backend.module.responses;

import com.krivonosovmarkov.persona.coursework_backend.module.TypeOfRelations;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpinionResponseDto {

    private Long id;
    private String name;
    private String description;
    private Boolean isGood;

}
