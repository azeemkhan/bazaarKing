package com.tresmoto.batch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited
@Table(name = "JOB_NOTIFICATION_DETAILS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDetails {


    @Id
    @Column(name = "job_type", nullable = false)
    private String jobType;

    @Column(name = "mail_from", nullable = false)
    private String from;

    @Column(name = "mail_to", nullable = false)
    private String to;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "cc")
    private String cc;

    @Column(name = "bcc")
    private String bcc;

}
