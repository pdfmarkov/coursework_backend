package com.krivonosovmarkov.persona.coursework_backend.module.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDto {

    private Integer eventId;
    private String locationName;
    private String name;
    private String description;
    private LocalDateTime date;

}
