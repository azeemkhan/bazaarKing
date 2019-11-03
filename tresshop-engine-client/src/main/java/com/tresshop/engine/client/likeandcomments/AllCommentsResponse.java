package com.tresshop.engine.client.likeandcomments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AllCommentsResponse {
    @JsonProperty("customerId")
    private String customerId;
    @JsonProperty("comments")
    private List<Comment> commentList;
}
