package com.tresmoto.batch.entity;

import com.tresmoto.batch.constants.QueryConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@SqlResultSetMapping(name = QueryConstant.SQL_MAPPING, classes = {
        @ConstructorResult(targetClass = PaymentDetails.class,
                columns = {
                        @ColumnResult(name = "PARENT_PE_TX_ID", type = String.class),
                        @ColumnResult(name = "TOTAL_AMOUNT", type = String.class),
                        @ColumnResult(name = "REF_PE_ID", type = String.class),
                        @ColumnResult(name = "ORDER_ID", type = String.class),
                        @ColumnResult(name = "TENANT_TX_ID", type = String.class),
                        @ColumnResult(name = "GATEWAY_CODE", type = String.class),
                        @ColumnResult(name = "GATEWAY_TX_ID", type = String.class),
                        @ColumnResult(name = "STATUS", type = String.class),
                        @ColumnResult(name = "PE_TX_ID", type = String.class),
                        @ColumnResult(name = "TX_SPLIT_UP_ID", type = String.class),
                        @ColumnResult(name = "CREATED_DATE", type = String.class)
                }
        )
})
@Entity
public class PaymentDetails {

    @Id
    private String id;

    public PaymentDetails(String parentTransactionId, String totalAmount, String refTransactionId,
                          String orderId, String tenantTransactionId, String gatewayCode,
                          String gatewayTransactionId, String status, String amount,
                          String transactionId, String transactionSplitUpId, String createdDate) {
        this.parentTransactionId = parentTransactionId;
        this.totalAmount = totalAmount;
        this.refTransactionId = refTransactionId;
        this.orderId = orderId;
        this.tenantTransactionId = tenantTransactionId;
        this.gatewayCode = gatewayCode;
        this.gatewayTransactionId = gatewayTransactionId;
        this.status = status;
        this.transactionSplitUpId = transactionSplitUpId;
        this.createdDate = createdDate;
        this.transactionId = transactionId;
    }


    @Column(name = "PARENT_PE_TX_ID")
    private String parentTransactionId;

    @Column(name = "REF_PE_ID")
    private String refTransactionId;


    @Column(name = "ORDER_ID")
    private String orderId;

    @Column(name = "TENANT_TX_ID")
    private String tenantTransactionId;

    @Column(name = "GATEWAY_CODE")
    private String gatewayCode;

    @Column(name = "GATEWAY_TX_ID")
    private String gatewayTransactionId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "TOTAL_AMOUNT")
    private String totalAmount;

    @Column(name = "TX_SPLIT_UP_ID")
    private String transactionSplitUpId;

    @Column(name = "CREATED_DATE")
    private String createdDate;

    @Column(name = "PE_TX_ID")
    private String transactionId;

}
