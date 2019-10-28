package com.tresmoto.batch.repositry;

import com.tresmoto.batch.constants.TransactionStatus;
import com.tresmoto.batch.constants.TransactionType;
import com.tresmoto.batch.entity.PaymentTransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface PaymentTransactionDetailsRepository extends JpaRepository<PaymentTransactionDetails, Integer> {

    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<PaymentTransactionDetails> findAllByCreatedOnBeforeAndTransactionStatusInAndTransactionTypeAndFiller2IsNull(Date date, List<TransactionStatus> status, TransactionType transactionType);

    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<PaymentTransactionDetails> findAllByTransactionTypeAndCreatedOnGreaterThanEqualAndCreatedOnLessThan(
            TransactionType transactionType, Date startTime, Date endTime);
}
