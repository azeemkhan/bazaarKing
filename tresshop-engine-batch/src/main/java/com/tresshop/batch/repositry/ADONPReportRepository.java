package com.tresmoto.batch.repositry;

import com.tresmoto.batch.constants.QueryConstant;
import com.tresmoto.batch.entity.PaymentDetails;
import com.tresmoto.batch.entity.ReportJobDetails;
import org.springframework.stereotype.Component;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Component
public class ADONPReportRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ReportJobDetails> fetchLastReportJobDetails() {
        Query result = entityManager.createNativeQuery(QueryConstant.REPORT_JOB_QUERY, PaymentDetails.class);
        return result.getResultList();

    }
}
