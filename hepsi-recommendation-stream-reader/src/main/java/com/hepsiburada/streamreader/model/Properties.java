package com.hepsiburada.streamreader.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Properties {
    @JsonProperty("productid")
    private String productId;
}
