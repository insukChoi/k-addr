package com.example.kaddr.api.juso.dto;

import lombok.Getter;
import lombok.NonNull;

public enum JusoReturnType {
    RO("로"),
    GIL("길")
    ;

    @Getter
    private final String description;

    JusoReturnType(String description) {
        this.description = description;
    }

    public static JusoReturnType defineType(@NonNull final String address) {
        if (address.endsWith(RO.description)) return RO;
        else return GIL;
    }
}