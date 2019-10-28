package com.tresmoto.batch.writer;

import com.tresmoto.batch.model.EventRequest;
import com.tresmoto.batch.provider.EventHandlerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class S2SEventHandlerWriter implements ItemWriter<EventRequest> {

    private static final Logger log = LoggerFactory.getLogger(S2SEventHandlerWriter.class);

    @Autowired
    private EventHandlerProvider eventHandlerProvider;


    @Override
    public void write(List<? extends EventRequest> eventRequests) {
        eventRequests.forEach((eventRequest) -> {
                    eventHandlerProvider.exchangeData(eventRequest);
                }
        );
    }

}
