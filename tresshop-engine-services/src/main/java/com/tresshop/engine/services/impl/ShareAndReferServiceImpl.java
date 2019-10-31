package com.tresshop.engine.services.impl;

import com.tresshop.engine.base.exception.RewardException;
import com.tresshop.engine.base.exception.ShareAndReferException;
import com.tresshop.engine.client.constants.ResponseConstants;
import com.tresshop.engine.client.enums.RewardTypes;
import com.tresshop.engine.client.enums.ShareTypes;
import com.tresshop.engine.client.rewards.ShareAndReferRequest;
import com.tresshop.engine.client.rewards.ShareAndReferResponse;
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

import java.util.List;
import java.util.Optional;

@Component
public class ShareAndReferServiceImpl implements ShareAndReferService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

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
                        RewardTypes.REFER,
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

        rewardsService.createReward(shareAndReferRequest.getFromUser());

        return populateShareAndReferResponse("Referred Successfully. Congratulations! won scratch card");
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
        Optional<List<ShareAndReferEntity>> shareAndReferEntities = Optional.empty();
        try {
            shareAndReferEntities =
                    shareAndReferRepository.findUserDataByType(
                            shareAndReferRequest.getFromUser(),
                            RewardTypes.SHARE.getName());
        } catch (Exception ex) {
            log.error("Something went wrong while fetching share from: {}, type: {}",
                    shareAndReferRequest.getFromUser(), shareAndReferRequest.getType());
        }

        if (shareAndReferEntities.isPresent() && isRewardEligible(shareAndReferEntities.get())) {
            rewardsService.createReward(shareAndReferRequest.getFromUser());
            return populateShareAndReferResponse("Congratulations! won scratch card");
        }

        return populateShareAndReferResponse(
                String.format(ResponseConstants.POINTS_ADDED_SUCCESS_MSG,
                        getShareRewardPoints()));
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
            throw new RewardException(HttpStatus.BAD_REQUEST, "Not a valid share type");
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

    private Integer getRewardThreshold() {
        //TODO call admin to get threshold
        return 1000;
    }

    private Integer getShareRewardPoints() {
        //TODO call admin to get reward points
        return 100;
    }

    private Integer getReferRewardPoints() {
        //TODO call admin to get reward points
        return 1000;
    }
}
