package com.tresmoto.batch.processor;


import com.tresmoto.batch.entity.EventHandler;
import com.tresmoto.batch.model.EventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class EventHandlerProcessor implements ItemProcessor<EventHandler, EventRequest> {

    private static final Logger log = LoggerFactory.getLogger(EventHandlerProcessor.class);

    @Override
    public EventRequest process(EventHandler eventHandler) throws Exception {
        log.debug("convert entity to event request for id {}" +
                " and payment transaction id {}", eventHandler.getId(), eventHandler.getPaymentEngineTransactionId());
        EventRequest eventRequest = new EventRequest();
        eventRequest.setEvent(eventHandler.getEvent());
        eventRequest.setId(eventHandler.getId());
        eventRequest.setPaymentEngineTransactionId(eventHandler.getPaymentEngineTransactionId());
        eventRequest.setStatus(eventHandler.getStatus());
        eventRequest.setCreatedDate(eventHandler.getCreatedOn());
        return eventRequest;
    }
}
