package com.tresshop.engine.services.productcategories;

import com.tresshop.engine.client.productcategories.AllCategoryResponse;
import com.tresshop.engine.client.productcategories.AllVendorResponse;
import com.tresshop.engine.client.productcategories.ProductCategoryRequest;
import com.tresshop.engine.client.productcategories.ProductCategoryResponse;

import java.util.List;

public interface ProductCategoryService {
    ProductCategoryResponse createProductCategory(ProductCategoryRequest productCategoryRequest);
    ProductCategoryResponse deleteProductCategory(ProductCategoryRequest productCategoryRequest);
    AllVendorResponse getAllVendorType();
    AllCategoryResponse getAllCategoryNames();
}
