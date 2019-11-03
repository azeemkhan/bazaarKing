package com.tresshop.engine.services.productcategories.impl;

import com.tresshop.engine.base.exception.ProductCategoryExceptions;
import com.tresshop.engine.client.productcategories.AllCategoryResponse;
import com.tresshop.engine.client.productcategories.AllVendorResponse;
import com.tresshop.engine.client.productcategories.ProductCategoryRequest;
import com.tresshop.engine.client.productcategories.ProductCategoryResponse;
import com.tresshop.engine.services.mapper.ProductCategoryMapper;
import com.tresshop.engine.services.productcategories.ProductCategoryService;
import com.tresshop.engine.storage.entity.ProductCategoryEntity;
import com.tresshop.engine.storage.repository.ProductCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ProductCategoryMapper productCategoryMapper;
    @Autowired
    ProductCategoryRepository productCategoryRepository;


    @Override
    public ProductCategoryResponse createProductCategory(ProductCategoryRequest productCategoryRequest) {

        ProductCategoryEntity productCategoryEntity = productCategoryMapper.populate(productCategoryRequest);
        try{
            Optional<ProductCategoryEntity> checkProductCategory = productCategoryRepository
                    .findCategoryByVendorAndCategoryName(productCategoryEntity.getVendorType()
                    ,productCategoryEntity.getCategoryName());
            if (checkProductCategory.isPresent()){
                log.error("ProductCategory already present : {}", productCategoryRequest.getCategoryName());
                throw new ProductCategoryExceptions.ProductCategoryException(
                        HttpStatus.CONFLICT,
                        "ProductCategory is already present.");
            } else {
                productCategoryRepository.save(productCategoryEntity);
                log.info("ProductCategory added successfully : {}",
                        productCategoryRequest.getCategoryName());
            }
        } catch (Exception ex){
            log.error(ex.getMessage());
            throw new ProductCategoryExceptions.ProductCategoryException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong. Try later!");
        }
        return populate(productCategoryEntity.getCategoryName(), "Added Category successfully");

    }

    @Override
    public ProductCategoryResponse deleteProductCategory(ProductCategoryRequest productCategoryRequest) {

        try {
            productCategoryRepository.deleteCategoryByVendorAndCategoryName(productCategoryRequest.getVendorType()
                            , productCategoryRequest.getCategoryName());

        }catch (Exception ex){
            log.error("Not able to delete: {}", productCategoryRequest.getCategoryName());
            throw new ProductCategoryExceptions.ProductCategoryException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Not able to delete category.");
        }
        return populate(productCategoryRequest.getCategoryName(),"Deleted category successfully.");
    }

    @Override
    public AllVendorResponse getAllVendorType() {
        List<String> vendorTypesList = new ArrayList<>();
        try {
            Optional<List<String>> vedorTypes = productCategoryRepository.getAllVendorType();
            if (vedorTypes.isPresent()){
                vendorTypesList = vedorTypes.get();
            }
        } catch (Exception ex){
            log.error(ex.getMessage());
            throw new ProductCategoryExceptions.ProductCategoryException(HttpStatus.INTERNAL_SERVER_ERROR, "Not able to get category.");
        }
        AllVendorResponse vendorResponse = new AllVendorResponse();
        vendorResponse.setVendors(vendorTypesList);
        return vendorResponse;
    }

    @Override
    public AllCategoryResponse getAllCategoryNames() {
        List<String> categoryList = new ArrayList<>();
        try {
            Optional<List<String>> categories = productCategoryRepository.getAllCategory();
            if (categories.isPresent()){
                categoryList = categories.get();
            }
        } catch (Exception ex){
            log.error(ex.getMessage());
            throw new ProductCategoryExceptions.ProductCategoryException(HttpStatus.INTERNAL_SERVER_ERROR, "Not able to get category.");
        }
        AllCategoryResponse categoryResponse = new AllCategoryResponse();
        categoryResponse.setCategories(categoryList);
        return categoryResponse;
    }

    public ProductCategoryResponse populate(String categoryName, String message){
        ProductCategoryResponse productCategoryResponse = new ProductCategoryResponse();
        productCategoryResponse.setMessage(message);
        productCategoryResponse.setCategoryName(categoryName);
        return productCategoryResponse;
    }
}
