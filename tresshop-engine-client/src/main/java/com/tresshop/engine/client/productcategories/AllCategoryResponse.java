package com.tresshop.engine.client.productcategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AllCategoryResponse {
    @JsonProperty("categories")
    private List<String> categories;
}
