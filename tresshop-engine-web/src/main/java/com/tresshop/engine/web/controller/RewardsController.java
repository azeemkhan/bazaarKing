package com.tresshop.engine.web.controller;

import com.tresshop.engine.client.rewards.RewardResponse;
import com.tresshop.engine.services.ShareAndRefer.RewardsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rewards")
public class RewardsController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RewardsService rewardsService;

    @GetMapping(value = "/{customerId}")
    public ResponseEntity<List<RewardResponse>> getAllRewards(@PathVariable String customerId) {
        return new ResponseEntity<>(
                rewardsService.getAllRewards(customerId), HttpStatus.OK);
    }

    @PostMapping(value = "redeem/{rewardId}")
    public ResponseEntity<RewardResponse> redeemReward(@PathVariable String rewardId) {
        return new ResponseEntity<>(
                rewardsService.redeemReward(rewardId), HttpStatus.OK);
    }
}
