package com.tresmoto.batch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PAYMENT_TX_SPLIT_UP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransactionSplitUp extends AbstractAttributes {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "PE_TX_ID")
    private String paymentEngineTransactionId;

    @Column(name = "TX_SPLIT_UP_ID")
    private String paymentTransactionSplitUpId;

    @Column(name = "TRANSACTION_TYPE")
    private String transactionType;

    @Column(name="GATEWAY_TX_ID")
    private String gateWayTransactionId;

    @Column(name = "STATUS")
    private String transactionStatus;

    @Column(name = "AMOUNT", nullable = false)
    String amount;

    @Column(name = "ERROR_CODE")
    private String errorCode;

    @Column(name = "INSTRUMENT_CODE")
    private String instrumentCode;

    @Column(name = "INSTRUMENT_INS_CODE")
    private String instrumentInstanceCode;


}
