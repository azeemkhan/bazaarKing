package com.tresshop.engine.client.sellers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class SellerRegistrationRequest {

    @JsonProperty(required = true)
    private String sellerFullName;
    private String emailId;

    @JsonProperty(required = true)
    private String phoneNumber;
    @JsonProperty(required = true)
    private String regionID;
    @JsonProperty(required = true)
    private String countryID;
    @JsonProperty(required = true)
    private String password;
    private String tin;
}
