package com.tresmoto.batch.notification;

import com.tresmoto.batch.constants.QueryConstant;
import com.tresmoto.batch.model.MailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Date;
import java.util.Properties;

@Component
public class EmailWriterImpl implements EmailWriter {

    private static final Logger log = LoggerFactory.getLogger(EmailWriterImpl.class);

    private Properties props;

    private Session session;

    @Value("${mail.smtp.server}")
    private String serverIp;

    @Value("${mail.transport.protocol}")
    private String protocol;

    @Value("${mail.smtp.starttls.enable}")
    private String starTTlsEnabled;

    @Value("${mail.smtp.connection-timeout}")
    private String connectionTimeout;

    @Value("${mail.smtp.timeout}")
    private String timeout;

    @Value("${mail.smtp.write-timeout}")
    private String writeTimeout;


    @PostConstruct
    public void init() throws Exception {
        props = new Properties();
        props.put("mail.smtp.host", serverIp);
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.starttls.enable", starTTlsEnabled);
        props.put("mail.smtp.connectiontimeout", connectionTimeout);
        props.put("mail.smtp.timeout", timeout);
        props.put("mail.smtp.writetimeout", writeTimeout);
        session = Session.getDefaultInstance(props);
    }


    @Override
    public void sendEmailWithBody(String fileAttachment, String zipFileAttachment, MailRequest mailRequest) {
        String fromEmail = mailRequest.getFrom();
        String toEmail = mailRequest.getTo();
        String subject = mailRequest.getSubject();
        String bodyText = mailRequest.getBody();
        String cc = mailRequest.getCc();
       // String bcc=mailRequest.getBcc();
        File refundReport = null;
        File refundZipReport = null;
        try {
            refundReport = new File(fileAttachment);
            refundZipReport = new File(zipFileAttachment);
            InternetAddress fromAddress = new InternetAddress(fromEmail);
            InternetAddress[] toAddress = InternetAddress.parse(toEmail);
            InternetAddress[] ccAddress = InternetAddress.parse(cc);
            //InternetAddress[] bccAddress = InternetAddress.parse(bcc);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(fromAddress);
            message.setRecipients(Message.RecipientType.TO, toAddress);
            message.setRecipients(Message.RecipientType.CC, ccAddress);
           // message.setRecipients(Message.RecipientType.BCC, bccAddress);
            message.setSubject(subject);
            message.setSentDate(new Date());
            MimeBodyPart messagePart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            messagePart.setText(bodyText);
            FileDataSource fileDataSource = new FileDataSource(refundZipReport);
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.setDataHandler(new DataHandler(fileDataSource));
            attachmentPart.setFileName(fileDataSource.getName());
            multipart.addBodyPart(attachmentPart);
            multipart.addBodyPart(messagePart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (Exception exp) {
            log.error("Exception {} while sending reporting details report mail and message is {} ", exp, exp.getMessage());
            throw new RuntimeException(QueryConstant.SEND_MAIL_FAILED_FOR_REPORT);
        } finally {
            if (null != refundZipReport) {
                refundReport.delete();
                refundZipReport.delete();
            }
        }
    }

    @Override
    public void sendJobFailureMessage(MailRequest mailRequest) {
        String fromEmail = mailRequest.getFrom();
        String toEmail = mailRequest.getTo();
        String subject = mailRequest.getSubject();
        String bodyText = mailRequest.getBody();
        String cc = mailRequest.getCc();
        //String bcc=mailRequest.getBcc();
        try {
            InternetAddress fromAddress = new InternetAddress(fromEmail);
            InternetAddress[] toAddress = InternetAddress.parse(toEmail);
            InternetAddress[] ccAddress = InternetAddress.parse(cc);
           // InternetAddress[] bccAddress = InternetAddress.parse(bcc);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(fromAddress);
            message.setRecipients(Message.RecipientType.TO, toAddress);
            message.setRecipients(Message.RecipientType.CC, ccAddress);
            //message.setRecipients(Message.RecipientType.BCC, bccAddress);
            message.setSubject(subject);
            message.setSentDate(new Date());
            MimeBodyPart messagePart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            messagePart.setText(bodyText);
            multipart.addBodyPart(messagePart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (Exception exp) {
            log.error("Exception {} while sending job failure mail and message is {} ", exp, exp.getMessage());
        }
    }
}
