package com.tresmoto.batch.provider;

import com.tresmoto.batch.model.EventRequest;
import com.tresmoto.batch.model.EventResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class EventHandlerProviderImpl implements EventHandlerProvider {

    private static final Logger log = LoggerFactory.getLogger(EventHandlerProviderImpl.class);

    @Autowired
    private RestTemplate eventRestTemplate;

    @Value("${rest.event.url}")
    private String eventUrl;

    @Override
    public void exchangeData(EventRequest eventRequest) {
        try {
            log.debug("call event api url for id {} and payment " +
                    "transaction id {}", eventRequest.getId(), eventRequest.getPaymentEngineTransactionId());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<EventRequest> entity = new HttpEntity<>(eventRequest, headers);
            ResponseEntity<EventResponse> responseEntity = eventRestTemplate.exchange(eventUrl, HttpMethod.POST, entity, EventResponse.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()
                    && null != responseEntity.getBody() && responseEntity.getBody().isSuccessFul()) {
                log.debug("successfully called event api url for id {} and payment " +
                        "transaction id {}", eventRequest.getId(), eventRequest.getPaymentEngineTransactionId());
                return;
            } else if (null != responseEntity.getBody() && null != responseEntity.getBody().getBaseError()) {
                log.info("retry failed while calling event controller for id {} and payment " +
                                "transaction id {} and error is {}", eventRequest.getId(), eventRequest.getPaymentEngineTransactionId(),
                        responseEntity.getBody().getBaseError().getDescription());
                return;
            }
            log.info("retry failed while calling event controller for id {} and payment " +
                    "transaction id {}", eventRequest.getId(), eventRequest.getPaymentEngineTransactionId());
        } catch (Exception exp) {
            log.error("exception occurred {} while calling event controller for id {} and payment " +
                    "transaction id {}", exp, eventRequest.getId(), eventRequest.getPaymentEngineTransactionId());
        }
    }
}
