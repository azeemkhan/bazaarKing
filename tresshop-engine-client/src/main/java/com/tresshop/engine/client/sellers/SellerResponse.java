package com.tresshop.engine.client.sellers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SellerResponse {


    @JsonProperty("fullName")
    private String sellerFullName;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("vendors")
    private String vendors;

    @JsonProperty("status")
    private int code;
    @JsonProperty("message")
    private String message;
    private String countryID;
    private String regionID;
    private String TIN;
}
