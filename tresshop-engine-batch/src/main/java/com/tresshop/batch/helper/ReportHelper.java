package com.tresmoto.batch.helper;

import com.tresmoto.batch.constants.QueryConstant;
import com.tresmoto.batch.constants.TransactionType;
import com.tresmoto.batch.entity.PaymentTransactionDetails;
import com.tresmoto.batch.entity.PaymentTransactionSplitUp;
import com.tresmoto.batch.entity.ReportJobDetails;
import com.tresmoto.batch.model.ADONPRequestDetails;
import com.tresmoto.batch.reader.ADONPReportReader;
import com.tresmoto.batch.repositry.ADONPReportRepository;
import com.tresmoto.batch.repositry.ReportJobDetailsRepository;
import com.tresmoto.batch.repositry.TransactionSplitUpRepository;
import com.ril.service.batch.entity.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class ReportHelper {

    private static final Logger log = LoggerFactory.getLogger(ADONPReportReader.class);

    @Autowired
    private ADONPReportRepository adonpReportRepository;

    @Autowired
    private com.tresmoto.batch.repositry.PaymentTransactionDetailsRepository PaymentTransactionDetailsRepository;

    @Autowired
    private ReportJobDetailsRepository reportJobDetailsRepository;

    @Autowired
    private TransactionSplitUpRepository transactionSplitUpRepository;

    @Value("${report.input-date-string}")
    private String inputString;

    @Value("${report.adonp.error-not-in-query}")
    private List<String> errorCodeNotIn;

    private Date startDate;


    @PostConstruct
    public void init() {
        try {
            DateFormat dateFormat = new SimpleDateFormat(QueryConstant.DATE_TIME_FORMAT);
            startDate = dateFormat.parse(inputString);
        } catch (Exception exp) {
            log.error("exception occurred while converting date");
        }
    }

    public ADONPRequestDetails getADONPReportDetails() {
        try {
            final ReportJobDetails reportJobDetails = reportJobDetailsRepository.findTopByOrderByEndTimeDesc();
            startDate = (reportJobDetails == null)
                    ? startDate : new DateTime(reportJobDetails.getEndTime().getTime()).withTimeAtStartOfDay().toDate();
            Date date = new DateTime().withTimeAtStartOfDay().toDate();
            CompletableFuture<List<PaymentTransactionDetails>> purchaseTransactions = CompletableFuture.supplyAsync(() ->
                    PaymentTransactionDetailsRepository.findAllByTransactionTypeAndCreatedOnGreaterThanEqualAndCreatedOnLessThan(
                            TransactionType.PURCHASE_TRANSACTION, startDate, date));
            CompletableFuture<List<PaymentTransactionDetails>> refundTransactions = CompletableFuture.supplyAsync(() ->
                    PaymentTransactionDetailsRepository.findAllByTransactionTypeAndCreatedOnGreaterThanEqualAndCreatedOnLessThan(
                            TransactionType.REFUND_TRANSACTION, startDate, date));
            CompletableFuture<List<PaymentTransactionSplitUp>> transactionSplitUpTransactions = CompletableFuture.supplyAsync(() ->
                    transactionSplitUpRepository.findAllByTransactionTypeAndCreatedOnGreaterThanEqualAndCreatedOnLessThan(
                            QueryConstant.PURCHASE_TRANSACTION, startDate, date));
            CompletableFuture<List<PaymentTransactionSplitUp>> refundSplitUpTransactions = CompletableFuture.supplyAsync(() ->
                    transactionSplitUpRepository.findAllByTransactionTypeAndCreatedOnGreaterThanEqualAndCreatedOnLessThan(
                            QueryConstant.REFUND_TRANSACTION, startDate, date));
            CompletableFuture.allOf(purchaseTransactions, refundTransactions, transactionSplitUpTransactions, refundSplitUpTransactions).join();
            Map<String, PaymentTransactionDetails> purchaseTransactionMap = new HashMap<>();
            Map<String, PaymentTransactionDetails> refundTransactionsMap = new HashMap<>();
            Map<String, List<PaymentTransactionSplitUp>> purchaseTransactionSplitUps = new HashMap<>();
            purchaseTransactions.get().forEach(purchaseTransaction -> {
                purchaseTransactionMap.put(purchaseTransaction.getPaymentEngineTransactionId(), purchaseTransaction);
            });
            refundTransactions.get().forEach(refundTransaction -> {
                refundTransactionsMap.put(refundTransaction.getPaymentEngineTransactionId(), refundTransaction);
            });
            transactionSplitUpTransactions.get().forEach(transactionSplitUp -> {
                if (null == purchaseTransactionSplitUps.get(transactionSplitUp.getPaymentEngineTransactionId())) {
                    List<PaymentTransactionSplitUp> paymentTransactionSplitUps = new ArrayList<>();
                    paymentTransactionSplitUps.add(transactionSplitUp);
                    purchaseTransactionSplitUps.put(transactionSplitUp.getPaymentEngineTransactionId(), paymentTransactionSplitUps);
                } else {
                    purchaseTransactionSplitUps.get(transactionSplitUp.getPaymentEngineTransactionId()).add(transactionSplitUp);
                }
            });
            ADONPRequestDetails adonpRequestDetails = new ADONPRequestDetails();
            ReportJobDetails reportJobDetail = new ReportJobDetails();
            reportJobDetail.setEndTime(date);
            reportJobDetail.setStartTime(startDate);
            adonpRequestDetails.setPurchaseTransactionMap(purchaseTransactionMap);
            adonpRequestDetails.setRefundTransactionsMap(refundTransactionsMap);
            adonpRequestDetails.setRefundTransactions(refundSplitUpTransactions.get());
            adonpRequestDetails.setPurchaseTransactionSplitUps(purchaseTransactionSplitUps);
            adonpRequestDetails.setReportJobDetails(reportJobDetail);
            return adonpRequestDetails;
        } catch (Exception exp) {
            log.info("exception {} occurred while generating recods for reporting from db  and message is {}", exp, exp.getMessage());
        }
        return null;
    }

}
