package com.tresshop.engine.client.enums;

public enum SystemProperties {
    PRICE("price"), //Price threshold to transfer into UPI
    SHARE("share"), //Share Points
    REWARD("reward"), //How much points needed to get reward
    PRICE_LIMIT("price_limit"), //How much points needed to get reward
    REFER("refer"); //Refer points

    private String name;

    SystemProperties(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
