package com.tresshop.engine.web.controller;

import com.tresshop.engine.client.sellers.SellerLoginRequest;
import com.tresshop.engine.client.sellers.SellerRegistrationRequest;
import com.tresshop.engine.client.sellers.SellerResponse;
import com.tresshop.engine.services.sellers.SellerServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("/sellers")
public class SellersController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SellerServices sellerServices;

    @PostMapping(
            value = "/login",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<SellerResponse> login(
            @RequestBody SellerLoginRequest sellerLoginRequest){
        return new ResponseEntity<>(sellerServices.login(sellerLoginRequest), HttpStatus.OK);
    }
    @PostMapping(
            value = "/register",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<SellerResponse> register(
            @RequestBody SellerRegistrationRequest sellerRegistrationRequest) {
        return new ResponseEntity<>(sellerServices.registration(sellerRegistrationRequest), HttpStatus.OK);
    }
    @PostMapping(
            value = "/changePassword",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<SellerResponse> changePassword(
            @RequestBody SellerLoginRequest sellerLoginRequest) {
        return new ResponseEntity<>(sellerServices.changePassword(sellerLoginRequest), HttpStatus.OK);
    }
    @PostMapping(
            value = "/deleteProfile",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<SellerResponse> deleteProfile(
            @RequestBody SellerLoginRequest sellerLoginRequest){
        return new ResponseEntity<>(sellerServices.deleteProfile(sellerLoginRequest), HttpStatus.OK);
    }
    @PostMapping(
            value = "/activateOrDeactivate/{makeActive}",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<SellerResponse> activateOrDeactivateProfile(
            @RequestBody SellerLoginRequest sellerLoginRequest, @PathVariable boolean makeActive){
        return new ResponseEntity<>(sellerServices.activateOrDeactivate(sellerLoginRequest, makeActive), HttpStatus.OK);
    }
}
