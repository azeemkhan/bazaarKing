package com.tresmoto.batch.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

@Entity
@Audited
@Table(name = "REPORT_JOB_DETAILS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportJobDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_id_generator")
    @SequenceGenerator(name = "report_id_generator", sequenceName = "REPORT_ID_SEQUENCE")
    @Column(name = "ID", length = 30, nullable = false, unique = true)
    private Integer id;

    @Column(name = "START_TIME")
    private Date startTime;

    @Column(name = "END_TIME")
    private Date endTime;

}
