package com.tresshop.engine.client.rewards;

import lombok.Data;

@Data
public class WalletInfo {
    private String customerId;

    private Double amount;

    private String customerUPI;

    private String mobileNumber;
}
