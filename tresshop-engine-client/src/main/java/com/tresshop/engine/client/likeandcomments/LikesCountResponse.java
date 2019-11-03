package com.tresshop.engine.client.likeandcomments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LikesCountResponse {
    @JsonProperty("totalLikes")
    private int totalCount;
    @JsonProperty("isLiked")
    private boolean isLiked;
}
