package com.tresmoto.engine.services;

import com.tresmoto.engine.client.UserLocationRequest;
import com.tresmoto.engine.client.shop.ShopLocationDetails;


import java.util.List;

public interface UserService {

    List<ShopLocationDetails> findShopByLocation(UserLocationRequest userLocationRequest);
}
