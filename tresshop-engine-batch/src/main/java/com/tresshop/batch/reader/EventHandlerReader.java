package com.tresmoto.batch.reader;

import com.tresmoto.batch.constants.Event;
import com.tresmoto.batch.constants.EventStatus;
import com.tresmoto.batch.entity.EventHandler;
import com.tresmoto.batch.repositry.EventHandlerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventHandlerReader implements ItemReader<EventHandler> {

    private static final Logger log = LoggerFactory.getLogger(EventHandlerReader.class);

    private int nextEventHandler;

    private List<EventHandler> eventHandlers;

    @Value("${spring.batch.event-status.not-to-query:SUCCESS,INVALID}")
    private List<EventStatus> eventStatusNotToQuery;

    @Autowired
    private EventHandlerRepository eventHandlerRepository;


    @Override
    public EventHandler read() throws Exception {
        log.debug("read data from EventHandler to do refund process");
        EventHandler eventHandler = null;
        if (nextEventHandler == 0) {
            eventHandlers = eventHandlerRepository.findAllByEventNotAndStatusNotIn(Event.S2S_SEND, eventStatusNotToQuery);
        }
        if (nextEventHandler < eventHandlers.size()) {
            eventHandler = eventHandlers.get(nextEventHandler++);
        } else {
            nextEventHandler = 0;
        }
        return eventHandler;
    }
}
