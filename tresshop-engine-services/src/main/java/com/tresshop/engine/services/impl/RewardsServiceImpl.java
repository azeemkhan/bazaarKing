package com.tresshop.engine.services.impl;

import com.tresshop.engine.base.exception.RewardException;
import com.tresshop.engine.base.exception.RewardNotFoundException;
import com.tresshop.engine.base.utils.UUIDUtils;
import com.tresshop.engine.client.constants.ResponseConstants;
import com.tresshop.engine.client.enums.RewardStatus;
import com.tresshop.engine.client.enums.RewardTypes;
import com.tresshop.engine.client.rewards.RewardResponse;
import com.tresshop.engine.services.RewardsService;
import com.tresshop.engine.storage.entity.RewardsEntity;
import com.tresshop.engine.storage.repository.RewardsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class RewardsServiceImpl implements RewardsService {

    @Autowired
    private RewardsRepository rewardsRepository;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UUIDUtils uuidUtils;

    @Override
    public void createReward(String customerId) {
        RewardsEntity rewardsEntity = populateRewardsEntity(customerId, RewardStatus.PENDING.getName());

        try {
            log.info("Creating Reward for customer id: {}", customerId);
            rewardsRepository.save(rewardsEntity);
        } catch (Exception ex) {
            log.error("Exception while creating reward for customer id: {} with exception: {}",
                    customerId, ex.getMessage());
            throw new RewardException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong while creating reward");
        }
    }

    @Override
    public RewardResponse redeemReward(String rewardId) {
        try {
            log.info("Fetching reward with reward id: {}", rewardId);
            Optional<RewardsEntity> rewardsEntity = rewardsRepository.findById(rewardId);

            if (!rewardsEntity.isPresent()) {
                log.error("Reward {} not found", rewardId);
                throw new RewardNotFoundException(HttpStatus.BAD_REQUEST, "Invalid Reward Id");
            }

            if (rewardsEntity.get().getStatus().equalsIgnoreCase(RewardStatus.COMPLETED.getName())) {
                log.error("Reward {} already redeemed", rewardId);
                throw new RewardException(HttpStatus.BAD_REQUEST, "Reward already redeemed");
            }

            updateRewardStatus(RewardStatus.COMPLETED.getName(), rewardId);
            return populateRewardResponse(
                    String.format(
                            ResponseConstants.REDEEM_REWARD_SUCCESS_MSG,
                            rewardsEntity.get().getDescription(),
                            rewardsEntity.get().getRewardType()
                                    .equalsIgnoreCase(RewardTypes.PRICE.getName()) ? "rupees" : ""),
                    rewardsEntity.get().getRewardId(),
                    rewardsEntity.get().getRewardType(),
                    RewardStatus.COMPLETED.getName());
        } catch (RewardNotFoundException ex) {
            log.error("Reward {} not found", rewardId);
            throw ex;
        } catch (RewardException ex) {
            log.error("Something went wrong for reward id: {} with exception: {}", rewardId, ex.getErrorMsg());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception while fetching reward for reward id: {} with exception: {}",
                    rewardId, ex.getMessage());
            updateRewardStatus(RewardStatus.FAILED.getName(), rewardId);
            throw new RewardException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong while creating reward");
        }
    }

    @Override
    public List<RewardResponse> getAllRewards(String customerId) {
        List<RewardResponse> rewardResponses = new ArrayList<>();
        try {
            log.info("Fetching rewards with customer id: {}", customerId);
            Optional<List<RewardsEntity>> rewardsEntities = rewardsRepository.findAllRewardsForCustomer(customerId);
            if (rewardsEntities.isPresent()) {
                rewardResponses = rewardsEntities.get().stream().map(rewardsEntity -> {
                    if (RewardStatus.COMPLETED.getName().equalsIgnoreCase(rewardsEntity.getStatus())) {
                        return populateRewardResponse(
                                rewardsEntity.getDescription(),
                                rewardsEntity.getRewardId(),
                                rewardsEntity.getRewardType(),
                                rewardsEntity.getStatus());
                    } else {
                        return populateRewardResponse(
                                null,
                                rewardsEntity.getRewardId(),
                                null,
                                rewardsEntity.getStatus());
                    }
                }).collect(Collectors.toList());
            }
        } catch (Exception ex) {
            log.error("Exception while fetching rewards for customer id: {} with exception: {}",
                    customerId, ex.getMessage());
            throw new RewardException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong while fetching rewards");
        }
        return rewardResponses;
    }

    private void updateRewardStatus(String rewardStatus, String rewardId) {
        try {
            rewardsRepository.updateRewardStatus(rewardStatus, rewardId);
        } catch (Exception ex) {
            log.error("Exception while redeeming reward {} with exception: {}",
                    rewardId, ex.getMessage());
            throw new RewardException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong while updating reward status");
        }
    }

    private RewardsEntity populateRewardsEntity(String customerId, String rewardStatus) {
        RewardsEntity rewardsEntity = new RewardsEntity();
        rewardsEntity.setCustomerId(customerId);
        rewardsEntity.setRewardId(uuidUtils.createUniqueIdForReward());
        rewardsEntity.setStatus(rewardStatus);

        RewardsEntity rewardValue = createRewardValue();
        rewardsEntity.setDescription(rewardValue.getDescription());
        rewardsEntity.setRewardType(rewardValue.getRewardType());
        rewardsEntity.setLastUpdatedTs(Timestamp.valueOf(LocalDateTime.now()));
        return rewardsEntity;
    }

    private RewardsEntity createRewardValue() {
        Random randomGenerator = new Random();
        RewardsEntity rewardsEntity = new RewardsEntity();
        rewardsEntity.setRewardType(RewardTypes.PRICE.getName());
        rewardsEntity.setDescription(String.valueOf(randomGenerator.nextInt(getRewardPriceValue())));
        return rewardsEntity;
    }

    private Integer getRewardPriceValue() {
        //TODO Get it from admin table
        return 10;
    }

    private RewardResponse populateRewardResponse(
            String message, String rewardId, String rewardType, String status) {
        RewardResponse rewardResponse = new RewardResponse();
        rewardResponse.setDescription(message);
        rewardResponse.setRewardId(rewardId);
        rewardResponse.setRewardType(rewardType);
        rewardResponse.setStatus(status);
        return rewardResponse;
    }
}
