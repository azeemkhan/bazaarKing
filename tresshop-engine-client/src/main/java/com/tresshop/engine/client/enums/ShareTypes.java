package com.tresshop.engine.client.enums;

public enum ShareTypes {
    PRODUCT("product"), MERCHANT("merchant");

    private String name;

    ShareTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
