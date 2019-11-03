package com.tresshop.engine.client.likeandcomments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WatchLaterResponse {

    @JsonProperty("customerId")
    private String customerId;

    @JsonProperty("products")
    private List<String> products;

}
