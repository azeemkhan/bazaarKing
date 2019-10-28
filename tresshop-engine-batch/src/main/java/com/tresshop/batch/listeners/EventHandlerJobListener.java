package com.tresmoto.batch.listeners;

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

import static com.tresmoto.batch.constants.MailConstant.*;


public class EventHandlerJobListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(EventHandlerJobListener.class);

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
                = notificationDetailsRepository.findByJobType(QueryConstant.EVENT_JOB);
        mailRequest = new HashMap<>();
        mailRequest.put(TO, notificationDetails.getTo());
        mailRequest.put(FROM, notificationDetails.getFrom());
        mailRequest.put(SUBJECT, notificationDetails.getSubject());
        mailRequest.put(CC, notificationDetails.getCc());
        mailRequest.put(BCC, notificationDetails.getBcc());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("EventHandlerJob job complete successfully");
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            emailWriter.sendJobFailureMessage(mailHelper.getMailRequest(mailRequest));
            log.info("EventHandlerJob job failed");
        } else if (jobExecution.getStatus() == BatchStatus.STOPPED) {
            log.info("EventHandlerJob job stopped");
        }
    }

}
