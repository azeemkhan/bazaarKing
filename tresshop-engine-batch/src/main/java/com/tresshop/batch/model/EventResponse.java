package com.tresmoto.batch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventResponse {

    @JsonProperty("isSuccessFul")
    private boolean isSuccessFul;

    @JsonProperty("paymentEngineTransactionId")
    private String paymentEngineTransactionId;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("baseError")
    private BaseError baseError;

}
