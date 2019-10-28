package com.tresmoto.batch.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
public class ReaderListener implements ItemReadListener {

    private static final Logger log = LoggerFactory.getLogger(ReaderListener.class);

    @Override
    public void beforeRead() {

    }

    @Override
    public void afterRead(Object o) {

    }

    @Override
    public void onReadError(Exception exp) {
        log.error("Exception {} occurred while reading data to do refund and message is {} ", exp, exp.getMessage());
    }
}
