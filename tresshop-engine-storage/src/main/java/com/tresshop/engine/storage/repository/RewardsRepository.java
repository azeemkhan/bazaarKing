package com.tresshop.engine.storage.repository;

import com.tresshop.engine.storage.entity.RewardsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RewardsRepository extends JpaRepository<RewardsEntity, String> {

    @Modifying
    @Query(value = "update rewards u set u.status = ?1 where u.reward_id = ?2", nativeQuery = true)
    @Transactional
    void updateRewardStatus(String rewardStatus, String rewardId);

    @Query(value = "SELECT * FROM rewards r where r.customer_id = ?1", nativeQuery = true)
    Optional<List<RewardsEntity>> findAllRewardsForCustomer(String customerId);
}
