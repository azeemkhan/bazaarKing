package com.tresshop.engine.web.controller;

import com.tresshop.engine.client.productcategories.AllCategoryResponse;
import com.tresshop.engine.client.productcategories.AllVendorResponse;
import com.tresshop.engine.client.productcategories.ProductCategoryRequest;
import com.tresshop.engine.client.productcategories.ProductCategoryResponse;
import com.tresshop.engine.services.productcategories.ProductCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ProductCategoryService productCategoryService;

    @PostMapping(
            value = "/createCategory",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<ProductCategoryResponse> createCategory(
            @RequestBody ProductCategoryRequest productCategoryRequest) {
        return new ResponseEntity<>(
                productCategoryService.createProductCategory(
                        productCategoryRequest), HttpStatus.OK);
    }

    @PostMapping(
            value = "/deleteProductCategory",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<ProductCategoryResponse> deleteProductCategory(
            @RequestBody ProductCategoryRequest productCategoryRequest) {
        return new ResponseEntity<>(productCategoryService.deleteProductCategory(productCategoryRequest), HttpStatus.OK);
    }
    @GetMapping(value = "/getAllVendorType")
    public ResponseEntity<AllVendorResponse> getAllVendorType(){
        return new ResponseEntity<>(productCategoryService.getAllVendorType(), HttpStatus.OK);
    }
    @GetMapping(value = "/getAllCategory")
    public ResponseEntity<AllCategoryResponse> getAllCategory(){
        return new ResponseEntity<>(productCategoryService.getAllCategoryNames(), HttpStatus.OK);
    }
}
