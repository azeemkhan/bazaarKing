package com.tresmoto.batch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tresmoto.batch.constants.Event;
import com.tresmoto.batch.constants.EventStatus;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventRequest {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("paymentEngineTransactionId")
    private String paymentEngineTransactionId;

    @JsonProperty("event")
    private Event event;

    @JsonProperty("status")
    private EventStatus status;

    @JsonProperty("createdDate")
    private Date createdDate;
}
