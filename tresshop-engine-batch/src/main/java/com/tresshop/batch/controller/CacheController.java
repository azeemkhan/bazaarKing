package com.tresmoto.batch.controller;

import com.tresmoto.batch.listeners.ADONPReportingJobListener;
import com.tresmoto.batch.listeners.EventHandlerJobListener;
import com.tresmoto.batch.listeners.PaymentTransactionListener;
import com.tresmoto.batch.writer.ADONPReportWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cache")
public class CacheController {

    private static final Logger log = LoggerFactory.getLogger(EventHandlerJobListener.class);


    @Autowired
    private ADONPReportingJobListener adonpReportingJobListener;

    @Autowired
    private EventHandlerJobListener eventHandlerJobListener;

    @Autowired
    private PaymentTransactionListener paymentTransactionListener;

    @Autowired
    private ADONPReportWriter adonpReportWriter;


    @GetMapping(value = "/all/reload")
    public ResponseEntity reloadAllCaches() {
        try {
            log.info("reload the notification details");
            adonpReportingJobListener.init();
            eventHandlerJobListener.init();
            paymentTransactionListener.init();
            adonpReportWriter.reload();
        } catch (Exception exp) {
            log.info("reload the notification details failed and message is {}", exp.getMessage());
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
