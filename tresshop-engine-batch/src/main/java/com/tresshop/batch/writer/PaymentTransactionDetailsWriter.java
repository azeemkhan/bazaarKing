package com.tresmoto.batch.writer;

import com.tresmoto.batch.model.ProcessPendingTransactionRequest;
import com.tresmoto.batch.model.ProcessPendingTransactionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class PaymentTransactionDetailsWriter implements ItemWriter<ProcessPendingTransactionRequest> {

    private static final Logger log = LoggerFactory.getLogger(PaymentTransactionDetailsWriter.class);

    @Autowired
    private RestTemplate eventRestTemplate;

    @Value("${rest.processTransaction.url}")
    private String processTransactionUrl;

    @Override
    public void write(List<? extends ProcessPendingTransactionRequest> processPendingTransactionRequests) {
        processPendingTransactionRequests.forEach((this::exchangeData));
    }

    private void exchangeData(final ProcessPendingTransactionRequest processPendingTransactionRequest) {
        try {
            log.debug("call transaction api url for id {} and payment " +
                    "transaction id {}", processPendingTransactionRequest.getId(),
                    processPendingTransactionRequest.getPaymentEngineTransactionId());
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            final HttpEntity<ProcessPendingTransactionRequest> entity = new HttpEntity<>(processPendingTransactionRequest, headers);
            final ResponseEntity<ProcessPendingTransactionResponse> responseEntity = eventRestTemplate
                    .exchange(processTransactionUrl, HttpMethod.POST, entity, ProcessPendingTransactionResponse.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()
                    && null != responseEntity.getBody() && responseEntity.getBody().isSuccessFul()) {
                log.debug("successfully called transaction api url for id {} and payment " +
                        "transaction id {}", processPendingTransactionRequest.getId(),
                        processPendingTransactionRequest.getPaymentEngineTransactionId());
                return;
            }
            log.info("retry failed while calling transaction controller for id {} and payment " +
                    "transaction id {}", processPendingTransactionRequest.getId(),
                    processPendingTransactionRequest.getPaymentEngineTransactionId());
        } catch (Exception exp) {
            log.error("exception occurred {} while calling transaction controller for id {} and payment " +
                    "transaction id {}", exp, processPendingTransactionRequest.getId(),
                    processPendingTransactionRequest.getPaymentEngineTransactionId());
        }
    }
}
