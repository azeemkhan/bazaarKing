package com.tresmoto.batch.entity;

import com.ril.service.batch.constants.*;
import com.tresmoto.batch.constants.*;
import com.tresmoto.service.batch.constants.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Audited
@Table(name = "PAYMENT_TX_DETAILS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransactionDetails extends AbstractAttributes {

    @Id
    @Column(name="id", nullable = false, unique = true)
    private Integer id;

    // set in case of refund - id the of the forward transaction against which refund got raised
    @Column(name = "PARENT_PE_TX_ID")
    private String parentPaymentEngineTransactionId;

    // acts as forward/refund transaction id
    @Column(name = "PE_TX_ID")
    private String paymentEngineTransactionId;

    @Column(name = "TENANT_CODE")
    private String tenantCode;

    // identifies the transaction as purchase/refund
    @Column(name = "TRANSACTION_TYPE")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "CUSTOMER_UUID")
    private String customerUUID;

    @Column(name = "GATEWAY_CODE")
    @Enumerated(EnumType.STRING)
    private PaymentGatewayType paymentGatewayCode;

    @Column(name = "ORDER_ID")
    private String orderId;

    @Column(name = "TENANT_TX_ID", unique = true)
    private String tenantTransactionId;


    @Column(name = "PAYMENT_CHANNEL")
    @Enumerated(EnumType.STRING)
    private PaymentChannelCode paymentChannelCode;

    @Column(name = "APP_TYPE")
    @Enumerated(EnumType.STRING)
    private AppType appType;


    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;


    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "TRANSACTION_DATE")
    private Long transactionDate;

    @Column(name = "ERROR_CODE")
    private String errorCode;

    @Lob
    @Column(name = "FILLER_1")
    private String filler1;

    //Using to update status change state of transaction
    @Lob
    @Column(name = "FILLER_2")
    private String filler2;
}
