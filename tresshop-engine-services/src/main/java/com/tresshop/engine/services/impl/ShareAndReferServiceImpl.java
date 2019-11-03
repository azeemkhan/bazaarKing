package com.tresshop.engine.services.impl;

import com.tresshop.engine.base.exception.ShareAndReferException;
import com.tresshop.engine.client.constants.ResponseConstants;
import com.tresshop.engine.client.enums.RewardTypes;
import com.tresshop.engine.client.enums.ShareTypes;
import com.tresshop.engine.client.rewards.ShareAndReferRequest;
import com.tresshop.engine.client.rewards.ShareAndReferResponse;
import com.tresshop.engine.services.AdminService;
import com.tresshop.engine.services.RewardsService;
import com.tresshop.engine.services.ShareAndReferService;
import com.tresshop.engine.services.mapper.ShareAndReferMapper;
import com.tresshop.engine.storage.entity.ShareAndReferEntity;
import com.tresshop.engine.storage.repository.ShareAndReferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ShareAndReferServiceImpl implements ShareAndReferService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminService adminService;

    @Autowired
    private ShareAndReferMapper shareAndReferMapper;

    @Autowired
    private ShareAndReferRepository shareAndReferRepository;

    @Autowired
    private RewardsService rewardsService;

    @Override
    public ShareAndReferResponse refer(ShareAndReferRequest shareAndReferRequest) {
        try {
            Optional<ShareAndReferEntity> referredUser =
                    shareAndReferRepository.findReferredUser(
                            shareAndReferRequest.getFromUser(),
                            shareAndReferRequest.getToUser(),
                            generateReferCode(shareAndReferRequest));

            if (referredUser.isPresent()) {
                log.info("Duplicate refer from: {}, to: {}",
                        shareAndReferRequest.getFromUser(), shareAndReferRequest.getToUser());
                return populateShareAndReferResponse(ResponseConstants.DUPLICATE_REFERRED_MSG);
            }
        } catch (Exception ex) {
            log.error("Something went wrong while checking duplicate refer from: {}, to: {}",
                    shareAndReferRequest.getFromUser(), shareAndReferRequest.getToUser());
            throw new ShareAndReferException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong while checking duplicate refer");
        }
        ShareAndReferEntity shareAndReferEntity =
                shareAndReferMapper.populateShareAndReferEntity(
                        shareAndReferRequest,
                        generateReferCode(shareAndReferRequest),
                        RewardTypes.REFER_PENDING,
                        getReferRewardPoints());

        try {
            shareAndReferRepository.save(shareAndReferEntity);
        } catch (Exception ex) {
            log.error("Something went wrong while checking inserting refer from: {}, to: {}",
                    shareAndReferRequest.getFromUser(), shareAndReferRequest.getToUser());
            throw new ShareAndReferException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong! Please refer again");
        }

        return populateShareAndReferResponse(ResponseConstants.REFERRED_SUCCESS_MSG);
    }

    @Override
    public ShareAndReferResponse redeemRefer(ShareAndReferRequest shareAndReferRequest) {
        if (StringUtils.isEmpty(shareAndReferRequest.getCode())) {
            log.error("Invalid refer code for shareAndReferRequest: {}",
                    shareAndReferRequest.toString());
            throw new ShareAndReferException(HttpStatus.BAD_REQUEST, "Invalid Refer Code");
        }
        try {
            Optional<ShareAndReferEntity> shareAndReferEntity =
                    shareAndReferRepository.findUserWithReferCode(
                            shareAndReferRequest.getFromUser(),
                            shareAndReferRequest.getCode());

            if (!shareAndReferEntity.isPresent()
                    || shareAndReferEntity.get().getType().equalsIgnoreCase(
                    RewardTypes.REFER_SUCCESS.getName())) {
                throw new ShareAndReferException(HttpStatus.BAD_REQUEST, "Invalid Refer Code");
            }

            //Mark Refer Entry to SUCCESS
            shareAndReferEntity.get().setType(RewardTypes.REFER_SUCCESS.getName());
            shareAndReferRepository.save(shareAndReferEntity.get());

            //Create rewards for referral and referee
            List<String> customerIds = new ArrayList<>();
            customerIds.add(shareAndReferEntity.get().getFromUser());
            customerIds.add(shareAndReferRequest.getFromUser());
            rewardsService.createRewards(customerIds);
        } catch (ShareAndReferException ex) {
            log.error("Invalid refer code: {}",
                    shareAndReferRequest.toString());
            throw ex;
        } catch (Exception ex) {
            log.error("Something went wrong while applying refer code: {}",
                    shareAndReferRequest.toString());
            throw new ShareAndReferException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong! Please try again");
        }

        return populateShareAndReferResponse(ResponseConstants.REFERRED_SCRATCH_CARD_WON_MSG);
    }

    @Override
    public Integer getTotalPoints(String customerId) {
        int shareCounts = fetchShareCount(customerId);
        return (shareCounts * getShareRewardPoints()) % getRewardThreshold();
    }

    @Override
    public ShareAndReferResponse share(ShareAndReferRequest shareAndReferRequest) {
        ShareAndReferEntity shareAndReferEntity =
                shareAndReferMapper.populateShareAndReferEntity(
                        shareAndReferRequest,
                        generateShareCode(shareAndReferRequest),
                        RewardTypes.SHARE,
                        getShareRewardPoints());

        try {
            shareAndReferRepository.save(shareAndReferEntity);
        } catch (Exception ex) {
            log.error("Something went wrong while inserting share from: {}, to: {}",
                    shareAndReferRequest.getFromUser(), shareAndReferRequest.getToUser());
            throw new ShareAndReferException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong while inserting share");
        }
        int shareCounts = fetchShareCount(shareAndReferRequest.getFromUser());

        if (isRewardEligible(shareCounts)) {
            rewardsService.createReward(shareAndReferRequest.getFromUser());
            return populateShareAndReferResponse("Congratulations! won scratch card");
        }

        return populateShareAndReferResponse(
                String.format(ResponseConstants.POINTS_ADDED_SUCCESS_MSG,
                        getShareRewardPoints()));
    }

    private int fetchShareCount(String customerId) {
        int shareCounts = 0;
        try {
            shareCounts = shareAndReferRepository.findCountUserDataByType(
                    customerId,
                    RewardTypes.SHARE.getName());
        } catch (Exception ex) {
            log.error("Something went wrong while fetching share from: {}, type: {}",
                    customerId, RewardTypes.SHARE.getName());
        }
        return shareCounts;
    }

    private String generateShareCode(ShareAndReferRequest shareAndReferRequest) {
        if (StringUtils.isEmpty(shareAndReferRequest.getCode()) ||
                StringUtils.isEmpty(shareAndReferRequest.getType())) {
            throw new ShareAndReferException(HttpStatus.BAD_REQUEST, "Empty Code/Type Id");
        }

        StringBuilder code = new StringBuilder();
        if (shareAndReferRequest.getType().equalsIgnoreCase(ShareTypes.PRODUCT.getName())) {
            code.append(ShareTypes.PRODUCT.getName()).append("_").append(shareAndReferRequest.getCode());
        } else if (shareAndReferRequest.getType().equalsIgnoreCase(ShareTypes.MERCHANT.getName())) {
            code.append(ShareTypes.MERCHANT.getName()).append("_").append(shareAndReferRequest.getCode());
        } else {
            throw new ShareAndReferException(HttpStatus.BAD_REQUEST, "Not a valid share type");
        }
        return code.toString();
    }

    private String generateReferCode(ShareAndReferRequest shareAndReferRequest) {
        //TODO generate unique code
        StringBuilder code = new StringBuilder();
        code.append(shareAndReferRequest.getFromUser());
        return code.toString();
    }

    private ShareAndReferResponse populateShareAndReferResponse(String message) {
        ShareAndReferResponse shareAndReferResponse = new ShareAndReferResponse();
        shareAndReferResponse.setDisplayMessage(message);
        return shareAndReferResponse;
    }

    private boolean isRewardEligible(List<ShareAndReferEntity> shareAndReferEntities) {
        return (shareAndReferEntities.size() * getShareRewardPoints()) % getRewardThreshold() == 0;
    }

    private boolean isRewardEligible(int sharesCount) {
        return (sharesCount * getShareRewardPoints()) % getRewardThreshold() == 0;
    }

    private Integer getRewardThreshold() {
        try {
            return adminService.getRewardPointThreshold();
        } catch (Exception ex) {
            log.error("Failed to fetch Reward threshold with exception: {}", ex.getMessage());
            throw new ShareAndReferException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!");
        }
    }

    private Integer getShareRewardPoints() {
        try {
            return adminService.getSharePointThreshold();
        } catch (Exception ex) {
            log.error("Failed to fetch Share points with exception: {}", ex.getMessage());
            throw new ShareAndReferException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!");
        }
    }

    private Integer getReferRewardPoints() {
        try {
            return adminService.getReferPointThreshold();
        } catch (Exception ex) {
            log.error("Failed to fetch refer points with exception: {}", ex.getMessage());
            throw new ShareAndReferException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!");
        }
    }
}
