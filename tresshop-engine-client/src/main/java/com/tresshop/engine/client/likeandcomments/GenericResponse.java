package com.tresshop.engine.client.likeandcomments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GenericResponse {
    @JsonProperty("code")
    private int status;
    @JsonProperty("message")
    private String message;
}
