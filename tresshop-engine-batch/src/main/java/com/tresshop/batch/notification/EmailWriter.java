package com.tresmoto.batch.notification;

import com.tresmoto.batch.model.MailRequest;

public interface EmailWriter {


    void sendEmailWithBody(String fileAttachment,String zipFileAttachment, MailRequest mailRequest);

    void sendJobFailureMessage(MailRequest mailRequest);


}
