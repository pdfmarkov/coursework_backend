package com.krivonosovmarkov.persona.coursework_backend.module.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;
import java.time.LocalDateTime;

@Data
@SqlResultSetMapping(name = "customEventResponseDto", entities = {
        @EntityResult(
                entityClass = CustomEventResponseDto.class,
                fields = {
                        @FieldResult(name = "eventId", column = "event_id"),
                        @FieldResult(name = "locationName", column = "locationname"),
                        @FieldResult(name = "name", column = "name"),
                        @FieldResult(name = "description", column = "description"),
                        @FieldResult(name = "date", column = "date"),
                        @FieldResult(name = "isGood", column = "is_event_good_for_doctor"),
                }
        )
})
@AllArgsConstructor
@NoArgsConstructor
public class CustomEventResponseDto {

    private long eventId;
    private String locationName;
    private String name;
    private String description;
    private LocalDateTime date;
    private Boolean isGood;

}
