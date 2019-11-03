package com.tresshop.engine.client.likeandcomments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentsAuditRequest {

    @JsonProperty(required = true)
    private String crId;

    @JsonProperty(required = true)
    private int commentQuality;

    @JsonProperty(required = true)
    private boolean isApproved;

}
