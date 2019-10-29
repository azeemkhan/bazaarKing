package com.tresshop.engine.services.shop.impl;

import com.tresshop.engine.base.utils.UUIDUtils;
import com.tresshop.engine.client.shop.ShopLocationDetails;
import com.tresshop.engine.services.shop.ShopService;
import com.tresshop.engine.storage.entity.MerchantLocationEntity;
import com.tresshop.engine.storage.repository.MerchantLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private MerchantLocationRepository merchantLocationRepository;

    @Autowired
    private UUIDUtils uuidUtils;

    public String onBoardShop(ShopLocationDetails shopLocationDetails) {
        MerchantLocationEntity merchantLocationEntity = new MerchantLocationEntity();
        merchantLocationEntity.setShopId(uuidUtils.createUniqueIdForMerchant());
        merchantLocationEntity.setShopName(shopLocationDetails.getShopName());
        merchantLocationEntity.setShopLatitude(shopLocationDetails.getLatitude());
        merchantLocationEntity.setShopLongitude(shopLocationDetails.getLongitude());
        merchantLocationEntity.setShopName(shopLocationDetails.getShopName());
        merchantLocationEntity.setShopDescription(shopLocationDetails.getShopDescription());
        merchantLocationRepository.save(merchantLocationEntity);
        return merchantLocationEntity.getShopId();
    }
}
