package com.tresshop.engine.client.rewards;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Rewards {

    @JsonProperty("rewards")
    List<RewardResponse> rewards;

    @JsonProperty("totalPrice")
    private String totalPrice;

    @JsonProperty("totalPoints")
    private String totalPoints;

    @JsonProperty("thresholdPrice")
    private String thresholdPrice;
}
