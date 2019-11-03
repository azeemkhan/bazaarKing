package com.tresshop.engine.storage.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "wallet")
@Getter
@Setter
@NoArgsConstructor
public class WalletEntity {

    @Id
    @Column(name = "customer_id", nullable = false, unique = true)
    private String customerId;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "customer_upi")
    private String customerUPI;

    @Column(name = "mobile_num")
    private String mobileNumber;

    @Column(name = "created_ts")
    private Timestamp createdTs;

    @Column(name = "last_Updated_ts", nullable = false)
    private String lastUpdatedTs;
}
