package com.tresshop.engine.client.likeandcomments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LikesRequest {

    @JsonProperty(required = true)
    private String itemLikedId;

    @JsonProperty(required = true)
    private String customerId;

    @JsonProperty(required = true)
    private String itemType;

    @JsonProperty(required = true)
    private boolean isLiked;
}
