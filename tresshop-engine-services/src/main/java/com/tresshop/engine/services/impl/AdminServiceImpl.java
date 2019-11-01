package com.tresshop.engine.services.impl;

import com.tresshop.engine.client.enums.SystemProperties;
import com.tresshop.engine.services.AdminService;
import com.tresshop.engine.storage.entity.SystemProperty;
import com.tresshop.engine.storage.repository.SystemPropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminServiceImpl implements AdminService {

    @Autowired
    private SystemPropertiesRepository systemPropertiesRepository;

    private Integer redeemPriceThreshold = null;
    private Integer rewardPointThreshold = null;
    private Integer sharePoint = null;
    private Integer referPoint = null;
    private Integer priceLimit = null;

    @Override
    public Integer getRedeemPriceThreshold() {
        if (redeemPriceThreshold == null) {
            redeemPriceThreshold = Integer.valueOf(getPropertyValue(SystemProperties.PRICE));
        }
        return redeemPriceThreshold;
    }

    @Override
    public Integer getRewardPointThreshold() {
        if (rewardPointThreshold == null) {
            rewardPointThreshold = Integer.valueOf(getPropertyValue(SystemProperties.REWARD));
        }
        return rewardPointThreshold;
    }

    @Override
    public Integer getSharePointThreshold() {
        if (sharePoint == null) {
            sharePoint = Integer.valueOf(getPropertyValue(SystemProperties.SHARE));
        }
        return sharePoint;
    }

    @Override
    public Integer getReferPointThreshold() {
        if (referPoint == null) {
            referPoint = Integer.valueOf(getPropertyValue(SystemProperties.REFER));
        }
        return referPoint;
    }

    @Override
    public Integer getPriceLimit() {
        if (priceLimit == null) {
            priceLimit = Integer.valueOf(getPropertyValue(SystemProperties.PRICE_LIMIT));
        }
        return priceLimit;
    }

    @Override
    public void createSystemProperty(String property, String value) {
        SystemProperty systemProperty = new SystemProperty(property, value);
        systemPropertiesRepository.save(systemProperty);
    }

    private String getPropertyValue(SystemProperties systemProperty) {
        Optional<SystemProperty> value = systemPropertiesRepository.findById(systemProperty.getName());
        return value.get().getPropertyValue();
    }
}
