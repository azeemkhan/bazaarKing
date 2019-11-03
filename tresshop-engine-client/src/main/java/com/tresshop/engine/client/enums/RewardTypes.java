package com.tresshop.engine.client.enums;

public enum RewardTypes {
    REFER_PENDING("refer_pending"), REFER_SUCCESS("refer_success"), SHARE("share"), PRICE("price"), OFFER("offer");

    private String name;

    RewardTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
