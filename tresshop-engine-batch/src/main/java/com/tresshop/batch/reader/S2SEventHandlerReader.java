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

import java.util.Date;
import java.util.List;

@Component
public class S2SEventHandlerReader implements ItemReader<EventHandler> {

    private static final Logger log = LoggerFactory.getLogger(S2SEventHandlerReader.class);

    private int nextS2SEventHandler;

    private List<EventHandler> s2sEventHandlers;

    @Value("${spring.batch.s2s-event-status.not-to-query:SUCCESS,INVALID}")
    private List<EventStatus> s2sEentStatusNotToQuery;

    @Value("${spring.batch.s2s-event-status.record-consider-in-minute:5}")
    private int minute;

    @Autowired
    private EventHandlerRepository eventHandlerRepository;


    @Override
    public EventHandler read() throws Exception {
        log.debug("read data from EventHandler to do refund process");
        EventHandler eventHandler = null;
        if (nextS2SEventHandler == 0) {
            s2sEventHandlers = eventHandlerRepository.findAllByEventAndCreatedOnBeforeAndStatusNotIn(
                    Event.S2S_SEND, new Date(System.currentTimeMillis() - 60000 * minute), s2sEentStatusNotToQuery);
        }
        if (nextS2SEventHandler < s2sEventHandlers.size()) {
            eventHandler = s2sEventHandlers.get(nextS2SEventHandler++);
        } else {
            nextS2SEventHandler = 0;
        }
        return eventHandler;
    }
}

