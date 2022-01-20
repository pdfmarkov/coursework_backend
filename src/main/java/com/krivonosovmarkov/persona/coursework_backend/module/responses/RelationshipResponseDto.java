package com.krivonosovmarkov.persona.coursework_backend.module.responses;

import com.krivonosovmarkov.persona.coursework_backend.module.TypeOfRelations;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelationshipResponseDto {

    private String name;
    private String surname;
    private TypeOfRelations type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
