package com.krivonosovmarkov.persona.coursework_backend.module.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HumanEventResponseDto {

    private Long id;
    private String locationName;
    private String name;
    private String description;
    private LocalDateTime date;
    private Boolean isGood;

}
