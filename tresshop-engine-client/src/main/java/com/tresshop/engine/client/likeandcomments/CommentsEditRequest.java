package com.tresshop.engine.client.likeandcomments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentsEditRequest {
    @JsonProperty(required = true)
    private String crId;
    @JsonProperty(required = true)
    private String comment;
}
