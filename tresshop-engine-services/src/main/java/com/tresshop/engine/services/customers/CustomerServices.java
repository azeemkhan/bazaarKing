package com.tresshop.engine.services.customers;

import com.tresshop.engine.client.customers.CustomerLoginRequest;
import com.tresshop.engine.client.customers.CustomerRegistrationRequest;
import com.tresshop.engine.client.customers.CustomerResponse;

public interface CustomerServices {
    CustomerResponse login(CustomerLoginRequest customerLoginRequest);
    CustomerResponse registration(CustomerRegistrationRequest customerRegistrationRequest);
    CustomerResponse changePassword(CustomerLoginRequest customerLoginRequest);
    CustomerResponse deleteProfile(CustomerLoginRequest customerLoginRequest);
}
