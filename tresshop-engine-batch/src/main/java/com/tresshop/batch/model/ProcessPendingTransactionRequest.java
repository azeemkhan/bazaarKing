package com.tresmoto.batch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessPendingTransactionRequest {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("paymentEngineTransactionId")
    private String paymentEngineTransactionId;

    @JsonProperty("tenantTransactionId")
    private String tenantTransactionId;
}
