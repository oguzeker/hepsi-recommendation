package com.hepsiburada.streamreader.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EventType {

    PRODUCT_VIEW("ProductView");

    @JsonValue
    private String value;

}
