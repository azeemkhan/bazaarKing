package com.tresmoto.batch.listeners;

import com.tresmoto.batch.constants.MailConstant;
import com.tresmoto.batch.constants.QueryConstant;
import com.tresmoto.batch.entity.NotificationDetails;
import com.tresmoto.batch.helper.MailHelper;
import com.tresmoto.batch.notification.EmailWriter;
import com.tresmoto.batch.repositry.NotificationDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


public class PaymentTransactionListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(PaymentTransactionListener.class);

    @Autowired
    private NotificationDetailsRepository notificationDetailsRepository;


    @Autowired
    private EmailWriter emailWriter;

    private Map<String, String> mailRequest;

    @Autowired
    private MailHelper mailHelper;

    @PostConstruct
    public void init() {
        NotificationDetails notificationDetails
                = notificationDetailsRepository.findByJobType(QueryConstant.PAYMENT_TRANSACTION_JOB);
        mailRequest = new HashMap<>();
        mailRequest.put(MailConstant.TO, notificationDetails.getTo());
        mailRequest.put(MailConstant.FROM, notificationDetails.getFrom());
        mailRequest.put(MailConstant.SUBJECT, notificationDetails.getSubject());
        mailRequest.put(MailConstant.CC, notificationDetails.getCc());
        mailRequest.put(MailConstant.BCC, notificationDetails.getBcc());
    }


    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("PaymentTransaction job complete successfully");
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.info("PaymentTransaction job failed");
            emailWriter.sendJobFailureMessage(mailHelper.getMailRequest(mailRequest));
        } else if (jobExecution.getStatus() == BatchStatus.STOPPED) {
            log.info("PaymentTransaction job stopped");
        }
    }
}
