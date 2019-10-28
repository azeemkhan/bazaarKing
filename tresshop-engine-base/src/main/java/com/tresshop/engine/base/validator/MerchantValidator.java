package com.tresmoto.engine.base.validator;

import com.tresmoto.engine.base.exception.MerchantException;
import com.tresmoto.engine.client.shop.ShopLocationDetails;
import org.springframework.stereotype.Component;

@Component
public class MerchantValidator {

    public void validateMerchantDetails(ShopLocationDetails shopLocationDetails) {
        if (null == shopLocationDetails.getShopName())
            throw new MerchantException();
    }
}
