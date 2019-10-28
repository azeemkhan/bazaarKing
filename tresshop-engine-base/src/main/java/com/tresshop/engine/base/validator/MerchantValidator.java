package com.tresshop.engine.base.validator;

import com.tresshop.engine.base.exception.MerchantException;
import com.tresshop.engine.client.shop.ShopLocationDetails;
import org.springframework.stereotype.Component;

@Component
public class MerchantValidator {

    public void validateMerchantDetails(ShopLocationDetails shopLocationDetails) {
        if (null == shopLocationDetails.getShopName())
            throw new MerchantException();
    }
}
