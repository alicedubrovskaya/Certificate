package com.epam.esm.model.enumeration;

public enum SortOrder {
    ASC("ASC"), DESC("DESC");

    private String type;

    SortOrder(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
