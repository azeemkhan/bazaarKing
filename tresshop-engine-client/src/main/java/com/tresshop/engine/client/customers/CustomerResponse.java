package com.tresshop.engine.client.customers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CustomerResponse {
    @JsonProperty("customerId")
    private String customerId;

    @JsonProperty("userFullName")
    private String userFullName;

    @JsonProperty("emailId")
    private String emailId;

    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("regionId")
    private String regionID;
    @JsonProperty("countryId")
    private String countryID;
    @JsonProperty("defaultLatitude")
    private double defaultLocationLat;
    @JsonProperty("defaultLongitude")
    private double defaultLocationLong;
    private String profileImageLink;
    @JsonProperty("message")
    private String message;
}
