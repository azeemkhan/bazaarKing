package com.tresshop.engine.client.customers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomerLoginRequest {

    @JsonProperty(required = true)
    private String phoneNumber;

    @JsonProperty(required = true)
    private String password;
}
