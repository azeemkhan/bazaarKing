package com.tresmoto.batch.entity;

import com.tresmoto.batch.constants.Event;
import com.tresmoto.batch.constants.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "EVENT_HANDLER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventHandler extends AbstractAttributes {

    @Id
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    @Column(name = "PE_TX_ID", nullable = false)
    private String paymentEngineTransactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "EVENT", nullable = false)
    private Event event;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EventStatus status;

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object eventHandler) {
        return false;
    }

}
