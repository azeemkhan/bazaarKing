package com.tresshop.engine.client.productcategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductCategoryRequest {


    @JsonProperty(required = true)
    private String categoryName;

    @JsonProperty(required = true)
    private String vendorType;

    private String possibleSize;

    private String possibleColor;

    private String possibleType;

    private String possibleQuality;

}
