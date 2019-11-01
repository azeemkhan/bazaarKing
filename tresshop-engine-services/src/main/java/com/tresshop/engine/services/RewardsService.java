package com.tresshop.engine.services;

import com.tresshop.engine.client.rewards.RewardResponse;
import com.tresshop.engine.client.rewards.Rewards;

import java.util.List;

public interface RewardsService {
    void createReward(String customerId);
    void createRewards(List<String> customerId);
    Rewards redeemReward(String rewardId);
    Rewards getAllRewards(String customerId);
}
