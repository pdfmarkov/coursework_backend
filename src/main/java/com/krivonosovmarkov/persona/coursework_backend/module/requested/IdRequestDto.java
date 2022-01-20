package com.krivonosovmarkov.persona.coursework_backend.module.requested;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdRequestDto {

    @NotNull
    private Long id;

}
