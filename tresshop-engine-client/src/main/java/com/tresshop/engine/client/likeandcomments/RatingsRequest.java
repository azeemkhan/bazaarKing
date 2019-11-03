package com.tresshop.engine.client.likeandcomments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RatingsRequest {

    @JsonProperty(required = true)
    private String itemRatingOnId;

    @JsonProperty(required = true)
    private String itemType;

    @JsonProperty(required = true)
    private String customerId;

    @JsonProperty(required = true)
    private double rating;
}
