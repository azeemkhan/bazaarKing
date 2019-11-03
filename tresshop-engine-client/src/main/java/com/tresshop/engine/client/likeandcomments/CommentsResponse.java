package com.tresshop.engine.client.likeandcomments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class CommentsResponse {

    @JsonProperty("customerId")
    private String customerId;

    @JsonProperty("comment")
    private String comment;

}
