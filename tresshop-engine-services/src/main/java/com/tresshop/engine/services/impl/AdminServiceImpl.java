package com.tresshop.engine.services.impl;

import com.tresshop.engine.base.exception.GenericException;
import com.tresshop.engine.base.utils.DateUtils;
import com.tresshop.engine.client.enums.SystemProperties;
import com.tresshop.engine.client.rewards.SystemBanner;
import com.tresshop.engine.services.AdminService;
import com.tresshop.engine.storage.entity.SystemBannerEntity;
import com.tresshop.engine.storage.entity.SystemProperty;
import com.tresshop.engine.storage.repository.SystemBannerRepository;
import com.tresshop.engine.storage.repository.SystemPropertiesRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AdminServiceImpl implements AdminService {

    @Autowired
    private SystemPropertiesRepository systemPropertiesRepository;

    @Autowired
    private SystemBannerRepository systemBannerRepository;

    @Autowired
    private DateUtils dateUtils;

    private Logger log = LoggerFactory.getLogger(this.getClass());

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

    @Override
    public void createBanner(SystemBanner systemBanner) {
        try {
            systemBannerRepository.save(populateSystemBannerEntity(systemBanner));
        } catch (Exception ex) {
            log.error("Exception occurred while creating banner request: {} with exception: {}",
                    systemBanner.toString(), ex.getMessage());
            throw new GenericException(HttpStatus.BAD_REQUEST, "Exception occurred while creating banner");
        }
    }

    @Override
    public void deleteBanner(String bannerId) {
        try {
            systemBannerRepository.deleteById(Long.valueOf(bannerId));
        } catch (Exception ex) {
            log.error("Exception occurred while deleting banner: {} with exception: {}",
                    bannerId, ex.getMessage());
        }
    }

    @Override
    public List<SystemBanner> getAllActiveBanner() {
        try {
            List<SystemBannerEntity> systemBannerEntities =
                    systemBannerRepository.findAllActiveBanners();

            if (CollectionUtils.isEmpty(systemBannerEntities)) {
                return Collections.emptyList();
            }

            List<SystemBanner> systemBanners = systemBannerEntities.parallelStream()
                    .map(this::populateSystemBanner)
                    .collect(Collectors.toList());

            return systemBanners;
        } catch (Exception ex) {
            log.error("Exception occurred while fetching all active banners with exception: {}",
                    ex.getMessage());
            throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception occurred while creating banner");
        }
    }

    @Override
    public List<SystemBanner> getAllBanner() {
        try {
            List<SystemBannerEntity> systemBannerEntities =
                    systemBannerRepository.findAll();

            if (CollectionUtils.isEmpty(systemBannerEntities)) {
                return Collections.emptyList();
            }

            List<SystemBanner> systemBanners = systemBannerEntities.parallelStream()
                    .map(this::populateSystemBanner)
                    .collect(Collectors.toList());

            return systemBanners;
        } catch (Exception ex) {
            log.error("Exception occurred while fetching all banners with exception: {}",
                    ex.getMessage());
            throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception occurred while creating banner");
        }
    }

    @Override
    public List<SystemBanner> getAllActiveBannerByType(String type) {
        List<SystemBanner> systemBanners = getAllActiveBanner();
        return systemBanners.parallelStream()
                .filter(systemBanner -> StringUtils.equalsIgnoreCase(type, systemBanner.getType()))
                .collect(Collectors.toList());
    }

    private SystemBannerEntity populateSystemBannerEntity(SystemBanner systemBanner) {
        SystemBannerEntity systemBannerEntity = new SystemBannerEntity();
        systemBannerEntity.setStartDate(dateUtils.convertDateToTimestamp(systemBanner.getStartDate()));
        systemBannerEntity.setEndDate(dateUtils.convertDateToTimestamp(systemBanner.getEndDate()));
        systemBannerEntity.setName(systemBanner.getName());
        systemBannerEntity.setTitle(systemBanner.getTitle());
        systemBannerEntity.setShortDescription(systemBanner.getShortDescription());
        systemBannerEntity.setDescription(systemBanner.getDescription());
        systemBannerEntity.setType(systemBanner.getType());
        systemBannerEntity.setImageUrl(systemBanner.getImageUrl());
        return systemBannerEntity;
    }

    private SystemBanner populateSystemBanner(SystemBannerEntity systemBannerEntity) {
        SystemBanner systemBanner = new SystemBanner();
        systemBanner.setId(systemBannerEntity.getBannerId());
        systemBanner.setStartDate(systemBannerEntity.getStartDate().toString());
        systemBanner.setEndDate(systemBannerEntity.getEndDate().toString());
        systemBanner.setName(systemBannerEntity.getName());
        systemBanner.setTitle(systemBannerEntity.getTitle());
        systemBanner.setShortDescription(systemBannerEntity.getShortDescription());
        systemBanner.setDescription(systemBannerEntity.getDescription());
        systemBanner.setType(systemBannerEntity.getType());
        systemBanner.setImageUrl(systemBannerEntity.getImageUrl());
        return systemBanner;
    }

    private String getPropertyValue(SystemProperties systemProperty) {
        Optional<SystemProperty> value = systemPropertiesRepository.findById(systemProperty.getName());
        return value.get().getPropertyValue();
    }
}
