package com.epam.esm.model;

public enum Currency {
    USD("USD"), EUR("EUR"), BYN("BYN"), RUB("RUB");

    private String type;

    Currency(String type) {
        this.type = type;
    }

    public Integer getId() {
        return ordinal();
    }

    public static Currency getById(Integer id) {
        return Currency.values()[id];
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
