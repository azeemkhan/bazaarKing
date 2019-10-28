package com.tresmoto.batch.repositry;

import com.tresmoto.batch.entity.PaymentTransactionSplitUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface TransactionSplitUpRepository extends JpaRepository<PaymentTransactionSplitUp, Integer> {

    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<PaymentTransactionSplitUp> findAllByTransactionTypeAndCreatedOnGreaterThanEqualAndCreatedOnLessThan
            (String transactionType, Date startTime, Date endTime);

    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<PaymentTransactionSplitUp> findAllByTransactionTypeAndCreatedOnGreaterThanEqualAndCreatedOnLessThanAndErrorCodeIsNull
            (String transactionType, Date startTime, Date endTime);


}
