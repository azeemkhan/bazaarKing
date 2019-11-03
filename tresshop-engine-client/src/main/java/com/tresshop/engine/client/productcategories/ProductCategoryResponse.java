package com.tresshop.engine.client.productcategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductCategoryResponse {
    @JsonProperty("message")
    private String message;
    @JsonProperty("categoryName")
    private String categoryName;
}
