package com.tresmoto.batch.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseTransactionReport {


    private String paymentEngineTransactionId;

    private String orderId;
}
