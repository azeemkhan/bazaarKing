package com.tresmoto.batch.model;

import com.ril.service.batch.entity.*;
import com.tresmoto.batch.entity.PaymentTransactionDetails;
import com.tresmoto.batch.entity.PaymentTransactionSplitUp;
import com.tresmoto.batch.entity.ReportJobDetails;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class ADONPRequestDetails {

    private ReportJobDetails reportJobDetails;
    private Map<String, PaymentTransactionDetails> purchaseTransactionMap;
    private Map<String, PaymentTransactionDetails> refundTransactionsMap;
    private List<PaymentTransactionSplitUp> refundTransactions;
    private Map<String, List<PaymentTransactionSplitUp>> purchaseTransactionSplitUps;

}
