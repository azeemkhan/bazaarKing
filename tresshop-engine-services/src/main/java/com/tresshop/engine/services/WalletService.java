package com.tresshop.engine.services;

import com.tresshop.engine.client.rewards.WalletInfo;

public interface WalletService {
    WalletInfo updateAmount(String customerId, Double amount);

    WalletInfo getCustomerWalletInfo(String customerId);

    WalletInfo transferAmount(String customerId, String upi, String mobNum, Double amount);
}
