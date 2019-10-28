package com.tresmoto.batch.repositry;

import com.tresmoto.batch.entity.PaymentEngineConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

public interface PaymentEngineConfigRepository extends JpaRepository<PaymentEngineConfig, Integer> {

    @Transactional(isolation = Isolation.READ_COMMITTED)
    PaymentEngineConfig findByKey(String key);
}
