package com.tresshop.engine.storage.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "MERCHANT_LOCATION_INFO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MerchantLocationEntity extends AbstractAttributes {

    @Id
    @Column(name = "ID", nullable = false, unique = true)
    private String shopId;

    @Column(name = "LONGITUDE", nullable = false, unique = true)
    private String shopLongitude;

    @Column(name = "LATITUDE", nullable = false)
    private String shopLatitude;

    @Column(name = "NAME", length = 4000, nullable = false, unique = true)
    private String shopName;

    @Column(name = "DESCRIPTION")
    @Lob
    private String shopDescription;

    @Column(name = "LANDMARK")
    private String landMark;

}
