package com.tresshop.engine.client.likeandcomments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentsRequest {

    @JsonProperty(required = true)
    private String itemCommentsOnId;
    @JsonProperty(required = true)
    private String customerId;
    @JsonProperty(required = true)
    private String itemType;
    @JsonProperty(required = true)
    private String comments;


}
