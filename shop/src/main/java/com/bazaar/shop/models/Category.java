package com.bazaar.shop.models;

public enum Category {
    FURNITURE("furniture");

    Category(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
