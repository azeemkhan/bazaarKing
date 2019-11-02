package com.tresshop.engine.storage.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "system_banners")
@IdClass(SystemBannerEntity.CompositeKeys.class)
@Getter
@Setter
@NoArgsConstructor
public class SystemBannerEntity {

    @Id
    @Column(name = "banner_id", nullable = false, unique = true, updatable = false)
    private Long bannerId;

    @Id
    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;

    @Id
    @Column(name = "end_date", nullable = false)
    private Timestamp endDate;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "created_ts", nullable = false)
    private Timestamp createdTs;

    @Column(name = "last_Updated_ts", nullable = false)
    private Timestamp lastUpdatedTs;

    //In Case of composite keys we have to create a IdClass
    static class CompositeKeys implements Serializable {
        private Long bannerId;
        private Timestamp startDate;
        private Timestamp endDate;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CompositeKeys that = (CompositeKeys) o;
            return Objects.equals(bannerId, that.bannerId) &&
                    Objects.equals(startDate, that.startDate) &&
                    Objects.equals(endDate, that.endDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(bannerId, startDate, endDate);
        }
    }
}
