package com.tresshop.engine.client.rewards;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RewardResponse {

    @JsonProperty("reward_Id")
    private String rewardId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("description")
    private String description;

    @JsonProperty("rewardType")
    private String rewardType;
}
