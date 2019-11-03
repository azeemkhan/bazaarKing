package com.tresshop.engine.services;

import com.tresshop.engine.client.enums.SystemProperties;
import com.tresshop.engine.client.rewards.SystemBanner;

import java.util.List;

public interface AdminService {
    Integer getRedeemPriceThreshold();

    Integer getRewardPointThreshold();

    Integer getSharePointThreshold();

    Integer getReferPointThreshold();

    Integer getPriceLimit();

    void createSystemProperty(String property, String value);

    void createBanner(SystemBanner systemBanner);

    void deleteBanner(String bannerId);

    List<SystemBanner> getAllActiveBanner();

    List<SystemBanner> getAllBanner();

    List<SystemBanner> getAllActiveBannerByType(String type);
}
