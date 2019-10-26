package com.bazaar.shop.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Sale {

    @JsonProperty("vendorId")
    private String vendorId;
    @JsonProperty("banner")
    private String banner;
    @JsonProperty("timing")
    private Timing timing;
    @JsonProperty("description")
    private String description;
}
