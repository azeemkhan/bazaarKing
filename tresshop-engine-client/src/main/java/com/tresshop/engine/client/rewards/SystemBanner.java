package com.tresshop.engine.client.rewards;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SystemBanner {
    private Long Id;

    private String startDate;

    private String endDate;

    private String name;

    private String title;

    private String shortDescription;

    private String description;

    private String type;

    private String imageUrl;
}
