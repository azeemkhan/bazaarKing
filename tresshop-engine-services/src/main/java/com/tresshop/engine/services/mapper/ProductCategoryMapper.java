package com.tresshop.engine.services.mapper;

import com.tresshop.engine.client.productcategories.ProductCategoryRequest;
import com.tresshop.engine.storage.entity.ProductCategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryMapper {
    public ProductCategoryEntity populate(ProductCategoryRequest productCategoryRequest){
        ProductCategoryEntity productCategoryEntity = new ProductCategoryEntity();
        productCategoryEntity.setVendorType(productCategoryRequest.getVendorType());
        productCategoryEntity.setCategoryName(productCategoryRequest.getCategoryName());
        productCategoryEntity.setPossibleColor(productCategoryRequest.getPossibleColor());
        productCategoryEntity.setPossibleQuality(productCategoryRequest.getPossibleQuality());
        productCategoryEntity.setPossibleSize(productCategoryRequest.getPossibleSize());
        productCategoryEntity.setPossibleType(productCategoryRequest.getPossibleType());
        return productCategoryEntity;
    }
}
