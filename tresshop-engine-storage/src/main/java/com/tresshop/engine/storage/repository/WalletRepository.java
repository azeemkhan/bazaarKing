package com.tresshop.engine.storage.repository;

import com.tresshop.engine.storage.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface WalletRepository extends JpaRepository<WalletEntity, String> {

    @Modifying
    @Query(value = "update wallet w set w.amount = w.amount + ?1 where w.customer_id = ?2", nativeQuery = true)
    @Transactional
    void updateWalletAmount(Double amount, String customerId);
}
