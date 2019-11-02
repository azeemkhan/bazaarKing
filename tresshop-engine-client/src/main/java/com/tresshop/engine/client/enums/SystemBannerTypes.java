package com.tresshop.engine.client.enums;

public enum SystemBannerTypes {
    ADVERTISEMENT("ad"), NOTIFICATION("notification"), PROMOTION("promotion");

    private String name;

    SystemBannerTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
