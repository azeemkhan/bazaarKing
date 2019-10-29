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
@Table(name = "share_and_refer")
@Getter
@Setter
@NoArgsConstructor
public class ShareAndReferEntity {

    @Id
    @Column(name = "from_user", nullable = false, unique = true)
    private String fromUser;

    @Id
    @Column(name = "from_user", nullable = false, unique = true)
    private String toUser;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "platform_to_share", nullable = false)
    private String platform;

    @Id
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "points", nullable = false)
    private Integer points;

    @Column(name = "created_ts", nullable = false)
    private Timestamp createdTs;

    @Column(name = "lastUpdated_ts", nullable = false)
    private Timestamp lastUpdatedTs;
}
