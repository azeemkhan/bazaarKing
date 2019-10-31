package com.tresshop.engine.client.rewards;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RewardsRequest {

    private String customerId;
    private String rewardType;
    private String description;
}
