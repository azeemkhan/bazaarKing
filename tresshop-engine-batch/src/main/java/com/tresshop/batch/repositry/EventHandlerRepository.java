package com.tresmoto.batch.repositry;


import com.tresmoto.batch.constants.Event;
import com.tresmoto.batch.constants.EventStatus;
import com.tresmoto.batch.entity.EventHandler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface EventHandlerRepository extends JpaRepository<EventHandler, Integer> {

    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<EventHandler> findAllByEventNotAndStatusNotIn(Event event, List<EventStatus> eventStatus);

    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<EventHandler> findAllByEventAndCreatedOnBeforeAndStatusNotIn(Event event, Date date, List<EventStatus> eventStatus);

    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<EventHandler> findAllByStatusNotIn(List<EventStatus> eventStatus);


}


