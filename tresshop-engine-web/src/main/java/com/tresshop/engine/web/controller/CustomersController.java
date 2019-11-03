package com.tresshop.engine.web.controller;

import com.tresshop.engine.client.customers.CustomerLoginRequest;
import com.tresshop.engine.client.customers.CustomerRegistrationRequest;
import com.tresshop.engine.client.customers.CustomerResponse;
import com.tresshop.engine.services.customers.CustomerServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CustomerServices customerServices;

    @PostMapping(
            value = "/login",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<CustomerResponse> login(
            @RequestBody CustomerLoginRequest customerLoginRequest){
        return new ResponseEntity<>(customerServices.login(customerLoginRequest), HttpStatus.OK);
    }
    @PostMapping(
            value = "/register",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<CustomerResponse> register(
            @RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        return new ResponseEntity<>(customerServices.registration(customerRegistrationRequest), HttpStatus.OK);
    }
    @PostMapping(
            value = "/changePassword",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<CustomerResponse> changePassword(
            @RequestBody CustomerLoginRequest customerLoginRequest) {
        return new ResponseEntity<>(customerServices.changePassword(customerLoginRequest), HttpStatus.OK);
    }
    @PostMapping(
            value = "/deleteProfile",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<CustomerResponse> deleteProfile(
            @RequestBody CustomerLoginRequest customerLoginRequest){
        return new ResponseEntity<>(customerServices.deleteProfile(customerLoginRequest), HttpStatus.OK);
    }

}
