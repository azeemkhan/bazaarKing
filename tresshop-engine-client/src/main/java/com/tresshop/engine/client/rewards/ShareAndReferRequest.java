package com.tresshop.engine.client.rewards;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShareAndReferRequest {

    @JsonProperty(required = true)
    private String fromUser;

    @JsonProperty(required = true)
    private String toUser;

    @JsonProperty(required = true)
    private String type;

    @JsonProperty(required = true)
    private String platform;

    @JsonProperty(required = false)
    private String code;
}
