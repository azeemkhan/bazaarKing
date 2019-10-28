package com.tresshop.engine.web.controller;

import com.tresmoto.engine.base.validator.MerchantValidator;
import com.tresmoto.engine.client.shop.ShopLocationDetails;
import com.tresmoto.engine.services.shop.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("/merchant")
public class MerchantLocationController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopService shopService;

    @Autowired
    private MerchantValidator merchantValidator;

    @PostMapping(
            value = "/details/register",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<String> findShopByLocation(@RequestBody ShopLocationDetails shopLocationDetails) {
        merchantValidator.validateMerchantDetails(shopLocationDetails);
        return new ResponseEntity<>(shopService.onBoardShop(shopLocationDetails), HttpStatus.OK);
    }
}
