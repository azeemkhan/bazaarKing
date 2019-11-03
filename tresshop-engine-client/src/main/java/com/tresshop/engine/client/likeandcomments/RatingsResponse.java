package com.tresshop.engine.client.likeandcomments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RatingsResponse {
    @JsonProperty("item")
    private String itemRatingOnId;
    @JsonProperty("type")
    private String itemType;
    @JsonProperty("averageRating")
    private double averageRating;

}
