package com.tresmoto.batch.model;

import lombok.Data;

@Data
public class MailRequest {

    private String from;

    private String to;

    private String subject;

    private String body;

    private String cc;

    private String bcc;
}
