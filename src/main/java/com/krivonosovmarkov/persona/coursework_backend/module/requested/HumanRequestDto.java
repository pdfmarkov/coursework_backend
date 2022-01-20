package com.krivonosovmarkov.persona.coursework_backend.module.requested;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class HumanRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String sex;
    @NotBlank
    private String race;
    @NotBlank
    private String gender;
    @NotBlank
    private String temperament;
    @NotBlank
    private String status;

}
