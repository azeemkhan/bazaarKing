package com.tresshop.engine.services.sellers.imp;

import com.tresshop.engine.base.exception.SellersException;
import com.tresshop.engine.base.utils.EncodeDecode;
import com.tresshop.engine.base.utils.UUIDUtils;
import com.tresshop.engine.client.sellers.SellerLoginRequest;
import com.tresshop.engine.client.sellers.SellerRegistrationRequest;
import com.tresshop.engine.client.sellers.SellerResponse;
import com.tresshop.engine.services.mapper.SellerMapper;
import com.tresshop.engine.services.sellers.SellerServices;
import com.tresshop.engine.storage.entity.SellersEntity;
import com.tresshop.engine.storage.repository.SellerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SellerServicesImpl implements SellerServices {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SellerMapper sellerMapper;
    @Autowired
    SellerRepository sellerRepository;

    @Override
    public SellerResponse registration(SellerRegistrationRequest sellerRegistrationRequest)
    {
        String sellerFirstName = sellerRegistrationRequest.getSellerFullName()
                .substring(0, sellerRegistrationRequest.getSellerFullName().indexOf(" "))
                .trim();
        SellersEntity sellersEntity = sellerMapper.populateSellerEntity(sellerRegistrationRequest,
                UUIDUtils.createVendorId(sellerRegistrationRequest.getCountryID(),
                        sellerRegistrationRequest.getRegionID(), sellerFirstName),
                true);
        try{
            Optional<SellersEntity> checkSeller = sellerRepository.findById(sellerRegistrationRequest.getPhoneNumber());
            if (checkSeller.isPresent()){
                log.error("Seller already registered : {}", sellerRegistrationRequest.getPhoneNumber());
                return populateSellerRegistrationResponse(sellersEntity
                        , HttpStatus.BAD_REQUEST.value()
                        , "Already Resgisterd!");
            } else {
                sellerRepository.save(sellersEntity);
                log.info("Seller registered successfully : {}",
                        sellerRegistrationRequest.getPhoneNumber());
            }
        } catch (Exception ex){
            log.error(ex.getMessage());
            throw new SellersException.SellerException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong. Try later!");
        }
        return populateSellerRegistrationResponse(sellersEntity, HttpStatus.OK.value(),
                "Successfully Registered.");

    }

    @Override
    public SellerResponse changePassword(SellerLoginRequest sellerLoginRequest) {
        SellersEntity sellersEntity = null;
        try {
            Optional<SellersEntity> sellerFetchedEntity = sellerRepository.findById(sellerLoginRequest.getPhoneNumber());
            if (sellerFetchedEntity.isPresent()){
                sellersEntity = sellerFetchedEntity.get();
                sellersEntity.setPassword(EncodeDecode.encodeToBase64(sellerLoginRequest.getPassword()));
                sellerRepository.save(sellersEntity);
                log.info("Change password for {}", sellerLoginRequest.getPhoneNumber());
            } else {
                log.error("Seller is not present: {}", sellerLoginRequest.getPhoneNumber());
                throw new SellersException.SellerNotFoundException(HttpStatus.BAD_REQUEST,
                        "Enter registered phone number.");
            }
        } catch (Exception ex){
            log.error(ex.getMessage());
            throw new SellersException.SellerException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong");
        }
        return populateSellerRegistrationResponse(sellersEntity,HttpStatus.OK.value(),
                "Successfully password changed.");
    }


    @Override
    public SellerResponse login(SellerLoginRequest sellerLoginRequest) {
        String base64Password = EncodeDecode.encodeToBase64(sellerLoginRequest.getPassword());
        String phoneNumber = sellerLoginRequest.getPhoneNumber();
        try {
            Optional<SellersEntity> sellersEntity = sellerRepository.findSellerByPhoneNumberAndPassword(
                    phoneNumber,
                    base64Password);
            if(sellersEntity.isPresent()){
                log.info("Successfully Logged in : {}", sellerLoginRequest.getPhoneNumber());
                return populateSellerRegistrationResponse(sellersEntity.get(), HttpStatus.OK.value(),
                        "Login Successful.");
            }
            else {
                SellerResponse sellerResponse = new SellerResponse();
                sellerResponse.setPhoneNumber(sellerLoginRequest.getPhoneNumber());
                sellerResponse.setMessage("Either phone number or password is wrong. Try again.");
                return sellerResponse;
            }
        }catch (Exception ex){
            log.error("Not able to login: {}", sellerLoginRequest.getPhoneNumber());
            throw new SellersException.SellerException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Login failed. Something went wrong.");
        }
    }

    @Override
    public SellerResponse deleteProfile(SellerLoginRequest sellerLoginRequest) {
        String base64Password = EncodeDecode.encodeToBase64(sellerLoginRequest.getPassword());
        String phoneNumber = sellerLoginRequest.getPhoneNumber();
        try {
            Optional<SellersEntity> sellersEntity = sellerRepository.findSellerByPhoneNumberAndPassword(
                    phoneNumber,
                    base64Password);
            if(sellersEntity.isPresent()){
                sellerRepository.delete(sellersEntity.get());
                log.info("Profile deleted : {}", sellerLoginRequest.getPhoneNumber());
                return populateSellerRegistrationResponse(sellersEntity.get(), HttpStatus.OK.value(),
                        "Profile deleted successfully.");
            }
            else {
                log.error("Could not delete the profile.");
                throw new SellersException.SellerNotFoundException(HttpStatus.BAD_REQUEST,
                        "Some thing wrong while getting user profile");
            }
        }catch (Exception ex){
            log.error("Not able to delete: {}", sellerLoginRequest.getPhoneNumber());
            throw new SellersException.SellerException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Not able to delete profile.");
        }
    }

    @Override
    public SellerResponse activateOrDeactivate(SellerLoginRequest sellerLoginRequest, boolean makeActive) {
        String base64Password = EncodeDecode.encodeToBase64(sellerLoginRequest.getPassword());
        String phoneNumber = sellerLoginRequest.getPhoneNumber();
        SellersEntity sellersEntity = null;
        try {
            Optional<SellersEntity> sellersFetchedEntity = sellerRepository.findSellerByPhoneNumberAndPassword(
                    phoneNumber,
                    base64Password);
            if(sellersFetchedEntity.isPresent()){
                sellersEntity = sellersFetchedEntity.get();
                sellersEntity.setStatus(makeActive);
                sellerRepository.save(sellersEntity);
                log.info("Profile updated : {}", sellerLoginRequest.getPhoneNumber());
                return populateSellerRegistrationResponse(sellersEntity, HttpStatus.OK.value(),
                        "Profile updated successfully.");
            }
            else {
                log.error("Could not update the profile.");
                return populateSellerRegistrationResponse(sellersEntity
                        , HttpStatus.BAD_REQUEST.value()
                        , "Could not update.");
            }
        }catch (Exception ex){
            log.error("Not able to update: {}", sellerLoginRequest.getPhoneNumber());
            throw new SellersException.SellerException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Not able to activate/deactivate profile.");
        }
    }

    public SellerResponse populateSellerRegistrationResponse(SellersEntity sellersEntity,int code, String message){
        SellerResponse sellerResponse = new SellerResponse();
        sellerResponse.setPhoneNumber(sellersEntity.getPhoneNumber());
        sellerResponse.setSellerFullName(sellersEntity.getUserFullName());
        sellerResponse.setVendors(sellersEntity.getVendorIds());
        sellerResponse.setCode(code);
        sellerResponse.setMessage(message);
        sellerResponse.setCountryID(sellersEntity.getCountryID());
        sellerResponse.setRegionID(sellersEntity.getRegionID());
        sellerResponse.setTIN(sellersEntity.getTIN());
        return sellerResponse;
    }
}
