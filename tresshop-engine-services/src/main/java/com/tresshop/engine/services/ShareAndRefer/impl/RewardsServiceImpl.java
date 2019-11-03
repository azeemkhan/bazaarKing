package com.tresshop.engine.services.ShareAndRefer.impl;

import com.tresshop.engine.base.exception.RewardException;
import com.tresshop.engine.base.exception.RewardNotFoundException;
import com.tresshop.engine.base.utils.UUIDUtils;
import com.tresshop.engine.client.constants.ResponseConstants;
import com.tresshop.engine.client.enums.RewardStatus;
import com.tresshop.engine.client.enums.RewardTypes;
import com.tresshop.engine.client.rewards.RewardResponse;
import com.tresshop.engine.services.ShareAndRefer.RewardsService;
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

    @Override
    public void createReward(String customerId) {

    }

    @Override
    public RewardResponse redeemReward(String rewardId) {
        return null;
    }

    @Override
    public List<RewardResponse> getAllRewards(String customerId) {
        return null;
    }
}

