package com.tresshop.engine.services.impl;

import com.tresshop.engine.base.exception.GenericException;
import com.tresshop.engine.base.exception.NotFoundException;
import com.tresshop.engine.client.rewards.WalletInfo;
import com.tresshop.engine.services.WalletService;
import com.tresshop.engine.storage.entity.WalletEntity;
import com.tresshop.engine.storage.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public WalletInfo updateAmount(String customerId, Double amount) {
        try {
            log.info("Updating wallet amount for customer id: {}, amount: {}", customerId, amount);

            WalletInfo walletInfo = getCustomerWalletInfo(customerId);

            WalletEntity walletEntity = walletRepository.save(
                    populateWalletEntity(
                            customerId,
                            walletInfo == null ? amount : walletInfo.getAmount() + amount));

            return populateWalletInfo(walletEntity);
        } catch (Exception ex) {
            log.error("Exception while updating wallet for customer id: {} with exception: {}",
                    customerId, ex.getMessage());
            throw new NotFoundException(HttpStatus.BAD_REQUEST, "Customer ID Not Valid");
        }
    }

    @Override
    public WalletInfo getCustomerWalletInfo(String customerId) {
        try {
            log.info("Fetching wallet info for customer id: {}", customerId);

            Optional<WalletEntity> walletEntity = walletRepository.findById(customerId);

            if (walletEntity.isPresent()) {
                return populateWalletInfo(walletEntity.get());
            } else {
                throw new NotFoundException(HttpStatus.BAD_REQUEST, "Customer ID Not Valid");
            }
        } catch (NotFoundException ex) {
            log.error("Wallet not found for customer id: {} with exception: {}",
                    customerId, ex.getMessage());
            return null;
        } catch (Exception ex) {
            log.error("Exception while fetching wallet for customer id: {} with exception: {}",
                    customerId, ex.getMessage());
            throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong, please try again");
        }
    }

    @Override
    public WalletInfo transferAmount(String customerId, String upi, String mobNum, Double amount) {
        WalletInfo walletInfo = getCustomerWalletInfo(customerId);
        //TODO Payment Integration
        if (amount == 0.0) {
            return updateAmount(customerId, 0.0);
        } else {
            return updateAmount(customerId, walletInfo.getAmount() - amount);
        }
    }

    private WalletEntity populateWalletEntity(String customerId, Double amount) {
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setCustomerId(customerId);
        walletEntity.setAmount(amount);
        return walletEntity;
    }

    private WalletInfo populateWalletInfo(WalletEntity walletEntity) {
        WalletInfo walletInfo = new WalletInfo();
        walletInfo.setCustomerId(walletEntity.getCustomerId());
        walletInfo.setAmount(walletEntity.getAmount());
        walletInfo.setCustomerUPI(walletEntity.getCustomerUPI());
        walletInfo.setMobileNumber(walletEntity.getMobileNumber());
        return walletInfo;
    }
}
