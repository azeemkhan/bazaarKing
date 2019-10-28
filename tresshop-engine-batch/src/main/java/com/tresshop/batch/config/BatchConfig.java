package com.tresmoto.batch.config;


import com.tresmoto.batch.entity.EventHandler;
import com.tresmoto.batch.entity.PaymentTransactionDetails;
import com.tresmoto.batch.listeners.ADONPReportingJobListener;
import com.tresmoto.batch.listeners.EventHandlerJobListener;
import com.tresmoto.batch.listeners.PaymentTransactionListener;
import com.tresmoto.batch.listeners.S2SEventHandlerJobListener;
import com.tresmoto.batch.model.ADONPRequestDetails;
import com.tresmoto.batch.model.EventRequest;
import com.tresmoto.batch.model.ProcessPendingTransactionRequest;
import com.tresmoto.batch.model.ReportDetails;
import com.tresmoto.batch.processor.ADONPReportProcessor;
import com.tresmoto.batch.processor.EventHandlerProcessor;
import com.tresmoto.batch.processor.PaymentTransactionDetailsProcessor;
import com.tresmoto.batch.processor.S2SEventHandlerProcessor;
import com.tresmoto.batch.reader.ADONPReportReader;
import com.tresmoto.batch.reader.EventHandlerReader;
import com.tresmoto.batch.writer.ADONPReportWriter;
import com.tresmoto.batch.writer.EventHandlerWriter;
import com.tresmoto.batch.writer.PaymentTransactionDetailsWriter;
import com.tresmoto.batch.writer.S2SEventHandlerWriter;
import com.tresmoto.batch.reader.PaymentTransactionDetailsReader;
import com.tresmoto.batch.reader.S2SEventHandlerReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EventHandlerReader eventHandlerReader;

    @Autowired
    private S2SEventHandlerReader s2SEventHandlerReader;

    @Autowired
    private ADONPReportReader adonpReportReader;

    @Autowired
    private PaymentTransactionDetailsReader paymentTransactionDetailsReader;

    @Autowired
    private EventHandlerWriter eventHandlerWriter;

    @Autowired
    private S2SEventHandlerWriter s2SEventHandlerWriter;

    @Autowired
    private PaymentTransactionDetailsWriter paymentTransactionDetailsWriter;

    @Autowired
    private ADONPReportWriter adonpReportWriter;

    @Autowired
    private EventHandlerProcessor eventHandlerProcessor;

    @Autowired
    private S2SEventHandlerProcessor s2SEventHandlerProcessor;

    @Autowired
    private ADONPReportProcessor adonpReportProcessor;

    @Autowired
    private PaymentTransactionDetailsProcessor paymentTransactionDetailsProcessor;

    @Autowired
    private ItemReadListener<EventHandler> eventHandlerItemReadListener;

    @Autowired
    private ItemReadListener<EventHandler> s2sEventHandlerItemReadListener;

    @Autowired
    private ItemReadListener<PaymentTransactionDetails> paymentTransactionDetailsItemReadListener;

    @Autowired
    private ItemReadListener<ReportDetails> reportItemReadListener;


    @Value("${spring.batch.chunk.size}")
    private Integer chunkSize;


    @Value("${spring.batch.adonp.chunk.size}")
    private Integer adonpChunkSize;

    @Bean
    public Step eventHandlerSteps() {
        log.debug("going to have step eventHandlerSteps");
        return stepBuilderFactory.get("eventHandlerSteps").<EventHandler, EventRequest>chunk(chunkSize).reader(eventHandlerReader).
                processor(eventHandlerProcessor).writer(eventHandlerWriter).faultTolerant().skip(Exception.class).
                skip(SQLException.class).retry(Exception.class).retryLimit(3).noRollback(Exception.class).skipLimit(20).
                listener(eventHandlerItemReadListener).build();
    }

    @Bean(name = "eventHandlerJob")
    public Job eventHandlerJob() {
        return jobs.get("eventHandlerJob").listener(getEventHandlerJobListener()).incrementer(new RunIdIncrementer()).flow(eventHandlerSteps()).
                end().build();
    }


    @Bean
    public Step s2sEventHandlerSteps() {
        log.debug("going to have step s2sEventHandlerSteps");
        return stepBuilderFactory.get("s2sEventHandlerSteps").<EventHandler, EventRequest>chunk(chunkSize).reader(s2SEventHandlerReader).
                processor(s2SEventHandlerProcessor).writer(s2SEventHandlerWriter).faultTolerant().skip(Exception.class).
                skip(SQLException.class).retry(Exception.class).retryLimit(3).noRollback(Exception.class).skipLimit(20).
                listener(s2sEventHandlerItemReadListener).build();
    }

    @Bean(name = "s2sEventHandlerJob")
    public Job s2sEventHandlerJob() {
        return jobs.get("s2sEventHandlerJob").listener(getS2SEventHandlerJobListener()).incrementer(new RunIdIncrementer()).flow(s2sEventHandlerSteps()).
                end().build();
    }

    @Bean
    public Step adonpReportSteps() {
        log.debug("going to have step adonpReportSteps");
        return stepBuilderFactory.get("adonpReportSteps").<ADONPRequestDetails, ReportDetails>chunk(adonpChunkSize).reader(adonpReportReader).
                processor(adonpReportProcessor).writer(adonpReportWriter).faultTolerant().skip(Exception.class).
                skip(SQLException.class).retry(Exception.class).retryLimit(3).noRollback(Exception.class).skipLimit(20).
                listener(reportItemReadListener).build();
    }

    @Bean(name = "adonpReportJob")
    public Job adonpReportJob() {
        return jobs.get("adonpReportJob").incrementer(new RunIdIncrementer()).listener(reportingJobListener()).flow(adonpReportSteps()).
                end().build();
    }

    @Bean
    public Step processTransactionSteps() {
        log.debug("going to have step processTransactionSteps");
        return stepBuilderFactory.get("processTransactionSteps").<PaymentTransactionDetails, ProcessPendingTransactionRequest>chunk(chunkSize).reader(paymentTransactionDetailsReader)
                .processor(paymentTransactionDetailsProcessor).writer(paymentTransactionDetailsWriter).faultTolerant()
                .skip(Exception.class).skip(SQLException.class).retry(Exception.class).retryLimit(3).noRollback(Exception.class)
                .skipLimit(20).listener(paymentTransactionDetailsItemReadListener).build();
    }

    @Bean(name = "processTransactionJob")
    public Job processTransactionJob() {
        return jobs.get("processTransactionJob").incrementer(new RunIdIncrementer()).listener(getPaymentTransactionListener()).flow(processTransactionSteps()).
                end().build();
    }

    @Bean
    public EventHandlerJobListener getEventHandlerJobListener() {
        return new EventHandlerJobListener();
    }

    @Bean
    public S2SEventHandlerJobListener getS2SEventHandlerJobListener() {
        return new S2SEventHandlerJobListener();
    }

    @Bean
    public PaymentTransactionListener getPaymentTransactionListener() {
        return new PaymentTransactionListener();
    }


    @Bean
    public ADONPReportingJobListener reportingJobListener() {
        return new ADONPReportingJobListener();
    }
}
