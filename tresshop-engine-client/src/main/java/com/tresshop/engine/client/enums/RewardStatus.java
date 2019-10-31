package com.tresshop.engine.client.enums;

public enum RewardStatus {

    PENDING("pending"), FAILED("failed"), COMPLETED("completed");

    private String name;

    RewardStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
