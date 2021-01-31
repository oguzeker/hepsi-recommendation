package com.hepsiburada.viewproducer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Context {
    @JsonProperty("source")
    private SourceType sourceType;
}
