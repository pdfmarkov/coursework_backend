package com.krivonosovmarkov.persona.coursework_backend.module;

public enum Status {

    ELITE(5),
    UPPER(4),
    LOWER(3),
    WORKING(2),
    POOR(1);

    private int value;

    Status(int i) {
        value = i;
    }

    public int getValue() {
        return value;
    }



}
