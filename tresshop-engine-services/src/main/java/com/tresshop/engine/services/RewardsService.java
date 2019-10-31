package com.tresshop.engine.services;

import com.tresshop.engine.client.rewards.RewardResponse;

import java.util.List;

public interface RewardsService {
    void createReward(String customerId);
    RewardResponse redeemReward(String rewardId);
    List<RewardResponse> getAllRewards(String customerId);
}
