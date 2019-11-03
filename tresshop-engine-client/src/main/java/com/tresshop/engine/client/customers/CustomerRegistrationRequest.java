package com.tresshop.engine.client.customers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomerRegistrationRequest {

    @JsonProperty(required = true)
    private String customerFullName;
    private String emailId;

    @JsonProperty(required = true)
    private String phoneNumber;

    @JsonProperty(required = true)
    private String password;

    @JsonProperty(required = true)
    private double defaultLat;

    @JsonProperty(required = true)
    private double defaultLong;
    @JsonProperty(required = true)
    private String regionID;
    @JsonProperty(required = true)
    private String countryID;

}
