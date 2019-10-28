package com.tresmoto.batch.reader;

import com.tresmoto.batch.constants.TransactionStatus;
import com.tresmoto.batch.constants.TransactionType;
import com.tresmoto.batch.entity.PaymentTransactionDetails;
import com.tresmoto.batch.repositry.PaymentEngineConfigRepository;
import com.tresmoto.batch.repositry.PaymentTransactionDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/* This batch job picks up all the purchase transactions which has been
either in Pending or Aborted state without any update for more than threshold days.
Then for such transactions, we inquire gateway about their status, if we receive
Success we rollback these transactions and mark them as failed, recording the
state change in field Filler2. If we get Failed, we rollback internal wallet
transaction and record the state change. If we get exception from gateway
while fetching status for more than certain period of time, we mark the status
as Failed and record the reason in Filler2.
*/


@Component
public class PaymentTransactionDetailsReader implements ItemReader<PaymentTransactionDetails> {

    private static final Logger log = LoggerFactory.getLogger(PaymentTransactionDetailsReader.class);

    @Autowired
    private PaymentTransactionDetailsRepository paymentTransactionDetailsRepository;

    @Autowired
    private PaymentEngineConfigRepository paymentEngineConfigRepository;

    @Value("${spring.batch.transaction-status.query:PENDING,ABORTED}")
    private List<TransactionStatus> transactionStatusQuery;
    private int nextPaymentTransactionDetails;
    private List<PaymentTransactionDetails> paymentTransactionDetailsList;

    @Override
    public PaymentTransactionDetails read() {
        log.debug("read data from PaymentTransactionDetails to update transaction status");
        final String KEY = "PENDING_THRESHOLD_TIME_IN_MS";
        PaymentTransactionDetails paymentTransactionDetails = null;
        final long thresholdTime = new Date().getTime() - Long.parseLong(paymentEngineConfigRepository.findByKey(KEY).getValue());
        if (nextPaymentTransactionDetails == 0) {
            paymentTransactionDetailsList = paymentTransactionDetailsRepository
                    .findAllByCreatedOnBeforeAndTransactionStatusInAndTransactionTypeAndFiller2IsNull(new Date(thresholdTime),
                            transactionStatusQuery, TransactionType.PURCHASE_TRANSACTION);
        }
        if (nextPaymentTransactionDetails < paymentTransactionDetailsList.size()) {
            paymentTransactionDetails = paymentTransactionDetailsList.get(nextPaymentTransactionDetails++);
        } else {
            nextPaymentTransactionDetails = 0;
        }
        return paymentTransactionDetails;
    }
}
