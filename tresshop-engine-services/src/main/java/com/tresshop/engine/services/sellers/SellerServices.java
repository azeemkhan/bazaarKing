package com.tresshop.engine.services.sellers;

import com.tresshop.engine.client.sellers.SellerLoginRequest;
import com.tresshop.engine.client.sellers.SellerRegistrationRequest;
import com.tresshop.engine.client.sellers.SellerResponse;

public interface SellerServices {
    SellerResponse login(SellerLoginRequest sellerLoginRequest);
    SellerResponse registration(SellerRegistrationRequest sellerRegistrationRequest);
    SellerResponse changePassword(SellerLoginRequest sellerLoginRequest);
    SellerResponse deleteProfile(SellerLoginRequest sellerLoginRequest);
    SellerResponse activateOrDeactivate(SellerLoginRequest sellerLoginRequest, boolean makeActive);
}
