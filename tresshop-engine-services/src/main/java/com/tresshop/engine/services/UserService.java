package com.tresshop.engine.services;

import com.tresshop.engine.client.UserLocationRequest;
import com.tresshop.engine.client.shop.ShopLocationDetails;


import java.util.List;

public interface UserService {

    List<ShopLocationDetails> findShopByLocation(UserLocationRequest userLocationRequest);
}
