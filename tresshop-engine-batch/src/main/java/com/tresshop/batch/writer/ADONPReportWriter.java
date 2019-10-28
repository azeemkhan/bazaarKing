package com.tresmoto.batch.writer;

import com.tresmoto.batch.constants.QueryConstant;
import com.tresmoto.batch.entity.NotificationDetails;
import com.tresmoto.batch.helper.MailHelper;
import com.tresmoto.batch.model.ReportDetails;
import com.tresmoto.batch.notification.EmailWriter;
import com.tresmoto.batch.repositry.NotificationDetailsRepository;
import com.tresmoto.batch.repositry.ReportJobDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tresmoto.batch.constants.MailConstant.*;

@Component
public class ADONPReportWriter implements ItemWriter<ReportDetails> {

    private static final Logger log = LoggerFactory.getLogger(EventHandlerWriter.class);

    @Autowired
    private NotificationDetailsRepository notificationDetailsRepository;

    @Value("${report.file.location}")
    private String fileLocation;

    @Value("${report.file.name}")
    private String fileName;

    @Value("${report.file.zip.location}")
    private String zipFileLocation;

    @Value("${report.file.zip.name}")
    private String zipFileName;

    @Value("${report.file.adonp-report-name}")
    private String refundReportName;

    @Value("${report.file.purchase-transaction-report-name}")
    private String transactionReportName;

    private String filePath;

    private String zipFilePath;

    @Autowired
    private EmailWriter emailWriter;

    private Map<String, String> mailRequest;

    @Autowired
    private MailHelper mailHelper;

    @PostConstruct
    public void init() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fileLocation);
        stringBuilder.append(fileName);
        filePath = stringBuilder.toString();
        StringBuilder zipStringBuilder = new StringBuilder();
        zipStringBuilder.append(zipFileLocation);
        zipStringBuilder.append(zipFileName);
        zipFilePath = zipStringBuilder.toString();
        NotificationDetails notificationDetails
                = notificationDetailsRepository.findByJobType(QueryConstant.ADONP_REPORT);
        mailRequest = new HashMap<>();
        mailRequest.put(TO, notificationDetails.getTo());
        mailRequest.put(FROM, notificationDetails.getFrom());
        mailRequest.put(SUBJECT, notificationDetails.getSubject());
        mailRequest.put(CC, notificationDetails.getCc());
        mailRequest.put(BCC, notificationDetails.getBcc());
        mailRequest.put(BODY, BODY_CONTENT);
    }


    public void reload() {
        NotificationDetails notificationDetails
                = notificationDetailsRepository.findByJobType(QueryConstant.ADONP_REPORT);
        mailRequest = new HashMap<>();
        mailRequest.put(TO, notificationDetails.getTo());
        mailRequest.put(FROM, notificationDetails.getFrom());
        mailRequest.put(SUBJECT, notificationDetails.getSubject());
        mailRequest.put(CC, notificationDetails.getCc());
        mailRequest.put(BCC, notificationDetails.getBcc());
        mailRequest.put(BODY, BODY_CONTENT);
    }



    @Autowired
    private ReportJobDetailsRepository reportJobDetailsRepository;

    @Override
    public void write(List<? extends ReportDetails> reportDetails) throws Exception {
        if (null != reportDetails && !reportDetails.isEmpty()
                && null != reportDetails.get(0).getReportJobDetails()) {
            try {
                emailWriter.sendEmailWithBody(filePath, zipFilePath, mailHelper.getMailRequest(mailRequest));
                reportJobDetailsRepository.save(reportDetails.get(0).getReportJobDetails());
            } catch (RuntimeException exp) {
                log.error("exception message is {}", exp.getMessage());

            }
        }
    }
}

