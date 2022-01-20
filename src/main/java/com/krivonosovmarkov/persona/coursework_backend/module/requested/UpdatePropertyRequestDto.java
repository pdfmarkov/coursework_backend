package com.krivonosovmarkov.persona.coursework_backend.module.requested;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdatePropertyRequestDto {
    @NotNull
    private Long propertyId;

    private Boolean isGood;

}
