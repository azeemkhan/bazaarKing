package com.tresmoto.batch.processor;

import com.tresmoto.batch.entity.PaymentTransactionDetails;
import com.tresmoto.batch.model.ProcessPendingTransactionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class PaymentTransactionDetailsProcessor implements ItemProcessor<PaymentTransactionDetails, ProcessPendingTransactionRequest> {

    private static final Logger log = LoggerFactory.getLogger(PaymentTransactionDetailsProcessor.class);

    @Override
    public ProcessPendingTransactionRequest process(final PaymentTransactionDetails paymentTransactionDetails) {

        log.debug("convert entity to request for id {}" +
                " and payment transaction id {}", paymentTransactionDetails.getId(),
                paymentTransactionDetails.getPaymentEngineTransactionId());
        final ProcessPendingTransactionRequest processPendingTransactionRequest = new ProcessPendingTransactionRequest();
        processPendingTransactionRequest.setId(paymentTransactionDetails.getId());
        processPendingTransactionRequest.setPaymentEngineTransactionId(paymentTransactionDetails.getPaymentEngineTransactionId());
        processPendingTransactionRequest.setTenantTransactionId(paymentTransactionDetails.getTenantTransactionId());
        return processPendingTransactionRequest;
    }
}
