package com.krivonosovmarkov.persona.coursework_backend.module.responses;

import lombok.Data;

@Data
public class MessageResponseDto {
    private String message;

    public MessageResponseDto(String message) {
        this.message = message;
    }
}
