package com.tresshop.engine.client.likeandcomments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class WatchLaterRequest {

    @JsonProperty(required = true)
    private String customerId;

    @JsonProperty(required = true)
    private String productId;

    @JsonProperty(required = true)
    private boolean isWatchLater;
}
