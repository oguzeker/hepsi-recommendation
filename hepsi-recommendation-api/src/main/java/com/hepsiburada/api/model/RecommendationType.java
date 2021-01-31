package com.hepsiburada.api.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RecommendationType {

    PERSONALIZED("personalized"),
    NON_PERSONALIZED("non-personalized");

    @JsonValue
    private String value;

}
