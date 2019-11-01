package com.tresshop.engine.services;

import com.tresshop.engine.client.enums.SystemProperties;

public interface AdminService {
    Integer getRedeemPriceThreshold();

    Integer getRewardPointThreshold();

    Integer getSharePointThreshold();

    Integer getReferPointThreshold();

    Integer getPriceLimit();

    void createSystemProperty(String property, String value);
}
