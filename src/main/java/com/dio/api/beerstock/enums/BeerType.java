package com.dio.api.beerstock.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum BeerType {

    LAGER("Lager"),
    MALZBIER("Malzbier"),
    WITBIER("Witbier"),
    WEISS("Weiss"),
    ALE("Ale"),
    IPA("IPA"),
    STOUT("Stout");

    private final String description;

    BeerType(String description) {
        this.description = description;
    }
}