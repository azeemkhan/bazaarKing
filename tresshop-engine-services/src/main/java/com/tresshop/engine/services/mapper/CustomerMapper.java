package com.tresshop.engine.services.mapper;

import com.tresshop.engine.base.utils.EncodeDecode;
import com.tresshop.engine.client.customers.CustomerRegistrationRequest;
import com.tresshop.engine.storage.entity.CustomersEntity;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class CustomerMapper {
    public CustomersEntity populateCustomerEntity(CustomerRegistrationRequest registrationRequest, String customerId) {
        CustomersEntity customersEntity = new CustomersEntity();
        customersEntity.setCustomerId(customerId);
        customersEntity.setPhoneNumber(registrationRequest.getPhoneNumber());
        customersEntity.setUserFullName(registrationRequest.getCustomerFullName());
        customersEntity.setEmailId(registrationRequest.getEmailId());
        customersEntity.setCountryID(registrationRequest.getCountryID());
        customersEntity.setRegionID(registrationRequest.getRegionID());
        customersEntity.setDefaultLocationLat(registrationRequest.getDefaultLat());
        customersEntity.setDefaultLocationLong(registrationRequest.getDefaultLong());
        customersEntity.setPassword(EncodeDecode.encodeToBase64(registrationRequest.getPassword()));
        customersEntity.setUpdationDate(Timestamp.valueOf(LocalDateTime.now()));
        return customersEntity;
    }
}
