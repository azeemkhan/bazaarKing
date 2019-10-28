package com.tresmoto.batch.repositry;

import com.tresmoto.batch.entity.NotificationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationDetailsRepository extends JpaRepository<NotificationDetails, Integer> {

    @Transactional(isolation = Isolation.READ_COMMITTED)
    NotificationDetails findByJobType(String jobType);
}
