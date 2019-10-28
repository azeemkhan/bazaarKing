package com.tresmoto.batch.helper;

import com.tresmoto.batch.constants.MailConstant;
import com.tresmoto.batch.model.MailRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MailHelper {

    public MailRequest getMailRequest(Map<String, String> mailData) {
        MailRequest mailRequest = new MailRequest();
        mailRequest.setTo(mailData.get(MailConstant.TO));
        mailRequest.setFrom(mailData.get(MailConstant.FROM));
        mailRequest.setSubject(mailData.get(MailConstant.SUBJECT));
        if (null == mailData.get(MailConstant.BODY)) {
            mailRequest.setBody(mailData.get(MailConstant.SUBJECT));
        } else {
            mailRequest.setBody(mailData.get(MailConstant.BODY));
        }
        mailRequest.setCc(mailData.get(MailConstant.CC));
        mailRequest.setBcc(mailData.get(MailConstant.BCC));
        return mailRequest;
    }

}
