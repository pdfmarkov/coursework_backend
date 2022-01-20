package com.krivonosovmarkov.persona.coursework_backend.module.requested;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefreshTokenRequestDto {

    @NotBlank
    private String refreshToken;

}
