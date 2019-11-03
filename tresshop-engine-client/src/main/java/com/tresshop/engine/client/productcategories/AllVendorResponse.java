package com.tresshop.engine.client.productcategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AllVendorResponse {
    @JsonProperty("vendorsType")
    private List<String> vendors;
}
