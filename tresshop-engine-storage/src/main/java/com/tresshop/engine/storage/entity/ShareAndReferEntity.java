package com.tresshop.engine.storage.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "share_and_refer")
@IdClass(ShareAndReferEntity.CompositeKeys.class)
@Getter
@Setter
@NoArgsConstructor
public class ShareAndReferEntity {

    @Id
    @Column(name = "from_user", nullable = false, unique = true)
    private String fromUser;

    @Id
    @Column(name = "to_user", nullable = false, unique = true)
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

    @Column(name = "last_Updated_ts", nullable = false)
    private Timestamp lastUpdatedTs;

    //In Case of composite keys we have to create a IdClass
    static class CompositeKeys implements Serializable {
        private String fromUser;
        private String code;
        private String toUser;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CompositeKeys ids = (CompositeKeys) o;
            return Objects.equals(fromUser, ids.fromUser) &&
                    Objects.equals(code, ids.code) &&
                    Objects.equals(toUser, ids.toUser);
        }

        @Override
        public int hashCode() {
            return Objects.hash(fromUser, code, toUser);
        }
    }
}
