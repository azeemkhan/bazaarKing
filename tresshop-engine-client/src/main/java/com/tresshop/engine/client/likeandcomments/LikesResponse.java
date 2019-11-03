package com.tresshop.engine.client.likeandcomments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class LikesResponse {

    @JsonProperty("customers")
    private List<String> customers;

}
