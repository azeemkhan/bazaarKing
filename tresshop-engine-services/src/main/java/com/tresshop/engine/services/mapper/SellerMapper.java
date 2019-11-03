package com.tresshop.engine.services.mapper;

import com.tresshop.engine.base.utils.EncodeDecode;
import com.tresshop.engine.client.sellers.SellerRegistrationRequest;
import com.tresshop.engine.storage.entity.SellersEntity;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class SellerMapper {

    public SellersEntity populateSellerEntity(SellerRegistrationRequest registrationRequest,
                                              String vendorIds,
                                              boolean makeActivate){
        SellersEntity sellersEntity = new SellersEntity();
        sellersEntity.setPhoneNumber(registrationRequest.getPhoneNumber());
        sellersEntity.setUserFullName(registrationRequest.getSellerFullName());
        sellersEntity.setEmailId(registrationRequest.getEmailId());
        sellersEntity.setCountryID(registrationRequest.getCountryID());
        sellersEntity.setRegionID(registrationRequest.getRegionID());
        sellersEntity.setVendorIds(vendorIds);
        sellersEntity.setStatus(makeActivate);
        sellersEntity.setPassword(EncodeDecode.encodeToBase64(registrationRequest.getPassword()));
        sellersEntity.setUpdationDate(Timestamp.valueOf(LocalDateTime.now()));
        return sellersEntity;
    }
}
