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
@Table(name = "rewards")
@Getter
@Setter
@NoArgsConstructor
public class RewardsEntity {

    @Id
    @Column(name = "reward_id", nullable = false, unique = true)
    private String rewardId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "type", nullable = false)
    private String rewardType;

    @Column(name = "description")
    private String description;

    @Column(name = "created_ts", nullable = false)
    private Timestamp createdTs;

    @Column(name = "lastUpdated_ts", nullable = false)
    private Timestamp lastUpdatedTs;
}
