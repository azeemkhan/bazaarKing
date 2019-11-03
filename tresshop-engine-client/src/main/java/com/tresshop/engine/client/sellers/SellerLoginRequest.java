package com.tresshop.engine.client.sellers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SellerLoginRequest {

    @JsonProperty(required = true)
    private String phoneNumber;

    @JsonProperty(required = true)
    private String password;
}
