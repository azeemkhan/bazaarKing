package com.tresshop.engine.services.impl;

import com.tresshop.engine.base.exception.NotFoundException;
import com.tresshop.engine.base.exception.RewardException;
import com.tresshop.engine.base.exception.RewardNotFoundException;
import com.tresshop.engine.base.utils.UUIDUtils;
import com.tresshop.engine.client.constants.ResponseConstants;
import com.tresshop.engine.client.enums.RewardStatus;
import com.tresshop.engine.client.enums.RewardTypes;
import com.tresshop.engine.client.rewards.RewardResponse;
import com.tresshop.engine.client.rewards.Rewards;
import com.tresshop.engine.client.rewards.WalletInfo;
import com.tresshop.engine.services.RewardsService;
import com.tresshop.engine.services.ShareAndReferService;
import com.tresshop.engine.services.WalletService;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class RewardsServiceImpl implements RewardsService {

    @Autowired
    private RewardsRepository rewardsRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private ShareAndReferService shareAndReferService;

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
    public void createRewards(List<String> customerIds) {
        List<RewardsEntity> rewardsEntities =
                customerIds.parallelStream()
                        .map(s -> populateRewardsEntity(s, RewardStatus.PENDING.getName()))
                        .collect(Collectors.toList());

        try {
            log.info("Creating Reward for customer ids: {}", customerIds);
            rewardsRepository.saveAll(rewardsEntities);
        } catch (Exception ex) {
            log.error("Exception while creating reward for customer id: {} with exception: {}",
                    customerIds, ex.getMessage());
            throw new RewardException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong while creating reward");
        }
    }

    @Override
    public Rewards redeemReward(String rewardId) {
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

            WalletInfo walletInfo = null;

            //Update wallet for this customer if reward is PRICE
            if (RewardTypes.PRICE.getName().equalsIgnoreCase(rewardsEntity.get().getRewardType())
                    && !rewardsEntity.get().getDescription().equalsIgnoreCase("0")) {
                walletInfo = walletService.updateAmount(
                        rewardsEntity.get().getCustomerId(),
                        Double.valueOf(rewardsEntity.get().getDescription()));
            }

            updateRewardStatus(RewardStatus.COMPLETED.getName(), rewardId);
            RewardResponse rewardResponse = populateRewardResponse(
                    String.format(
                            ResponseConstants.REDEEM_REWARD_SUCCESS_MSG,
                            rewardsEntity.get().getDescription(),
                            rewardsEntity.get().getRewardType()
                                    .equalsIgnoreCase(RewardTypes.PRICE.getName()) ? "rupees" : ""),
                    rewardsEntity.get().getRewardId(),
                    rewardsEntity.get().getRewardType(),
                    RewardStatus.COMPLETED.getName());

            List<RewardResponse> reward = new ArrayList<>();
            reward.add(rewardResponse);

            return populateRewards(reward, walletInfo != null ? walletInfo.getAmount().toString() : null, null);
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

    private Rewards populateRewards(List<RewardResponse> rewardResponses, String totalPrice, String totalPoints) {
        Rewards rewards = new Rewards();
        rewards.setRewards(rewardResponses);
        rewards.setTotalPoints(totalPoints);
        rewards.setTotalPrice(totalPrice);
        rewards.setThresholdPrice(String.valueOf(getRewardPriceThresholdValue()));
        return rewards;
    }

    @Override
    public Rewards getAllRewards(String customerId) {
        List<RewardResponse> rewardResponses = new ArrayList<>();
        try {
            log.info("Fetching rewards with customer id: {}", customerId);
            Optional<List<RewardsEntity>> rewardsEntities = rewardsRepository.findAllRewardsForCustomer(customerId);
            if (rewardsEntities.isPresent()) {
                rewardResponses = rewardsEntities.get().stream().map(rewardsEntity -> populateRewardResponse(
                        rewardsEntity.getDescription(),
                        rewardsEntity.getRewardId(),
                        rewardsEntity.getRewardType(),
                        rewardsEntity.getStatus())).collect(Collectors.toList());
            }

        } catch (Exception ex) {
            log.error("Exception while fetching rewards for customer id: {} with exception: {}",
                    customerId, ex.getMessage());
            throw new RewardException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong while fetching rewards");
        }

        Double totalPrice = 0.0;
        try {
            WalletInfo walletInfo = walletService.getCustomerWalletInfo(customerId);
            if (null != walletInfo) {
                totalPrice = walletInfo.getAmount();
            }
        } catch (Exception ex) {
            log.error("Unable to fetch wallet for customer id: {} with exception: {}",
                    customerId, ex.getMessage());
        }
        Integer totalPoints = shareAndReferService.getTotalPoints(customerId);

        return populateRewards(rewardResponses, String.valueOf(totalPrice), String.valueOf(totalPoints));
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

    private Integer getRewardPriceThresholdValue() {
        //TODO Get it from admin table
        return 100;
    }

    private RewardResponse populateRewardResponse(
            String message, String rewardId, String rewardType,
            String status) {
        RewardResponse rewardResponse = new RewardResponse();
        rewardResponse.setDescription(message);
        rewardResponse.setRewardId(rewardId);
        rewardResponse.setRewardType(rewardType);
        rewardResponse.setStatus(status);
        return rewardResponse;
    }
}
