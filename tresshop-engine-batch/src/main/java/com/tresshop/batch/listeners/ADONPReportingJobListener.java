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


public class ADONPReportingJobListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(EventHandlerJobListener.class);

    @Autowired
    private NotificationDetailsRepository notificationDetailsRepository;

    @Autowired
    private EmailWriter emailWriter;

    private Map<String, String> jobFailureMailRequest;


    @Autowired
    private MailHelper mailHelper;

    @PostConstruct
    public void init() {
        NotificationDetails notificationDetails
                = notificationDetailsRepository.findByJobType(QueryConstant.ADONP_JOB);
        jobFailureMailRequest = new HashMap<>();
        jobFailureMailRequest.put(TO, notificationDetails.getTo());
        jobFailureMailRequest.put(FROM, notificationDetails.getFrom());
        jobFailureMailRequest.put(SUBJECT, notificationDetails.getSubject());
        jobFailureMailRequest.put(CC, notificationDetails.getCc());
        jobFailureMailRequest.put(BCC, notificationDetails.getBcc());
    }


    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("adonp report job complete successfully");
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.info("adonp report job failed");
            emailWriter.sendJobFailureMessage(mailHelper.getMailRequest(jobFailureMailRequest));
        } else if (jobExecution.getStatus() == BatchStatus.STOPPED) {
            log.info("adonp report job stopped");
        }
    }
}
