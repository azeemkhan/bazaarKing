package com.tresmoto.batch.repositry;

import com.tresmoto.batch.entity.ReportJobDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

public interface ReportJobDetailsRepository extends JpaRepository<ReportJobDetails, Integer> {

    @Transactional(isolation = Isolation.READ_COMMITTED)
    ReportJobDetails findTopByOrderByEndTimeDesc();

}
