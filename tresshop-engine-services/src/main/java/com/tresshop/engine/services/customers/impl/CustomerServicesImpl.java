package com.tresshop.engine.services.customers.impl;

import com.tresshop.engine.base.exception.CustomersException;
import com.tresshop.engine.base.utils.EncodeDecode;
import com.tresshop.engine.base.utils.UUIDUtils;
import com.tresshop.engine.client.customers.CustomerLoginRequest;
import com.tresshop.engine.client.customers.CustomerRegistrationRequest;
import com.tresshop.engine.client.customers.CustomerResponse;
import com.tresshop.engine.services.customers.CustomerServices;
import com.tresshop.engine.services.mapper.CustomerMapper;
import com.tresshop.engine.storage.entity.CustomersEntity;
import com.tresshop.engine.storage.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class CustomerServicesImpl implements CustomerServices {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public CustomerResponse login(CustomerLoginRequest customerLoginRequest)
    {
        String base64Password = EncodeDecode.encodeToBase64(customerLoginRequest.getPassword());
        String phoneNumber = customerLoginRequest.getPhoneNumber();
        try {
            Optional<CustomersEntity> customersEntity = customerRepository.findCustomerByPhoneNumberAndPassword(
                    phoneNumber,
                    base64Password);
            if(customersEntity.isPresent()){
                log.info("Successfully Logged in : {}", customerLoginRequest.getPhoneNumber());
                return populateCustomerRegistrationResponse(customersEntity.get(),
                        "Login Successful.");
            }
            else {
                CustomerResponse customerResponse = new CustomerResponse();
                customerResponse.setPhoneNumber(customerLoginRequest.getPhoneNumber());
                customerResponse.setMessage("Either phone number or password is wrong. Try again.");
                return customerResponse;
            }
        }catch (Exception ex){
            log.error("Not able to login: {}", customerLoginRequest.getPhoneNumber());
            throw new CustomersException.CustomerException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Login failed. Something went wrong.");
        }
    }

    @Override
    public CustomerResponse registration(CustomerRegistrationRequest customerRegistrationRequest)
    {
        String customerId = UUIDUtils.createCustomerId(customerRegistrationRequest.getPhoneNumber());
        CustomersEntity customersEntity = customerMapper.populateCustomerEntity(customerRegistrationRequest, customerId);

        try{
            Optional<CustomersEntity> checkCustomer = customerRepository.findById(customerId);
            if (checkCustomer.isPresent()){
                log.error("Customer already registered : {}", customerRegistrationRequest.getPhoneNumber());
                throw new CustomersException.CustomerAlreadyRegisteredException(
                        HttpStatus.CONFLICT,
                        "Customer is already registered with this phone number.");
            } else {
                customerRepository.save(customersEntity);
                log.info("Customer registered successfully : {}",
                        customerRegistrationRequest.getPhoneNumber());
            }
        } catch (Exception ex){
            log.error(ex.getMessage());
            throw new CustomersException.CustomerException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong. Try later!");
        }
        return populateCustomerRegistrationResponse(customersEntity,
                "Successfully Registered.");

    }

    @Override
    public CustomerResponse changePassword(CustomerLoginRequest customerLoginRequest) {
        CustomersEntity customersEntity = null;
        try {
            String customerId = UUIDUtils.createCustomerId(customerLoginRequest.getPhoneNumber());
            Optional<CustomersEntity> customerFetchedEntity = customerRepository.findById(customerId);
            if (customerFetchedEntity.isPresent()){
                customersEntity = customerFetchedEntity.get();
                customersEntity.setPassword(EncodeDecode.encodeToBase64(customerLoginRequest.getPassword()));
                customerRepository.save(customersEntity);
                log.info("Change password for {}", customerLoginRequest.getPhoneNumber());
            } else {
                log.error("Customer is not present: {}", customerLoginRequest.getPhoneNumber());
                throw new CustomersException.CustomerNotFoundException(HttpStatus.BAD_REQUEST,
                        "Enter registered phone number.");
            }
        } catch (Exception ex){
            log.error(ex.getMessage());
            throw new CustomersException.CustomerException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong");
        }
        return populateCustomerRegistrationResponse(customersEntity,
                "Successfully password changed.");

    }

    @Override
    public CustomerResponse deleteProfile(CustomerLoginRequest customerLoginRequest) {

        String base64Password = EncodeDecode.encodeToBase64(customerLoginRequest.getPassword());
        String phoneNumber = customerLoginRequest.getPhoneNumber();
        try {
            Optional<CustomersEntity> customersEntity = customerRepository.findCustomerByPhoneNumberAndPassword(
                    phoneNumber,
                    base64Password);
            if(customersEntity.isPresent()){
                customerRepository.delete(customersEntity.get());
                log.info("Profile deleted : {}", customerLoginRequest.getPhoneNumber());
                return populateCustomerRegistrationResponse(customersEntity.get(),
                        "Profile deleted successfully.");
            }
            else {
                log.error("Could not delete the profile.");
                throw new CustomersException.CustomerNotFoundException(HttpStatus.BAD_REQUEST,
                        "Some thing wrong while getting user profile");
            }
        }catch (Exception ex){
            log.error("Not able to delete: {}", customerLoginRequest.getPhoneNumber());
            throw new CustomersException.CustomerException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Not able to delete profile.");
        }
    }


    public CustomerResponse populateCustomerRegistrationResponse(CustomersEntity customersEntity, String message){
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setPhoneNumber(customersEntity.getPhoneNumber());
        customerResponse.setUserFullName(customersEntity.getUserFullName());
        customerResponse.setEmailId(customersEntity.getEmailId());
        customerResponse.setProfileImageLink(customersEntity.getProfileImageLink());
        customerResponse.setMessage(message);
        customerResponse.setCountryID(customersEntity.getCountryID());
        customerResponse.setRegionID(customersEntity.getRegionID());
        customerResponse.setDefaultLocationLat(customersEntity.getDefaultLocationLat());
        customerResponse.setDefaultLocationLong(customersEntity.getDefaultLocationLong());
        customerResponse.setCustomerId(customersEntity.getCustomerId());
        return customerResponse;
    }
    public List<String> listOfVendors(String vendorsBlob){
        String vendorsStringList = vendorsBlob.substring(1, vendorsBlob.length() -1);
        return Arrays.asList(vendorsStringList.split(","));
    }
}
