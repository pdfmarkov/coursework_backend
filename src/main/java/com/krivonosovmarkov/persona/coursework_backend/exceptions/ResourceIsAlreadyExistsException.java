package com.krivonosovmarkov.persona.coursework_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceIsAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceIsAlreadyExistsException(String msg) {
        super(msg);
    }

}
