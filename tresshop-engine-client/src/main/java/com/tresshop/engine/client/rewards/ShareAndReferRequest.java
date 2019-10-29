package com.tresshop.engine.client.rewards;

import lombok.Data;

@Data
public class ShareAndReferRequest {

    private String fromUser;

    private String toUser;

    private String type;

    private String platform;
}
