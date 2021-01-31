package com.hepsiburada.viewproducer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class View {
    @JsonProperty("event")
    private EventType eventType;

    @JsonProperty("messageid")
    private String messageId;

    @JsonProperty("userid")
    private String userId;

    private Properties properties;

    private Context context;
}
