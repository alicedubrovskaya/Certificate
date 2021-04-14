package com.epam.esm.model;

import java.math.BigDecimal;

public class Price {
    private BigDecimal cost;
    private Currency currency;

    public BigDecimal getCost() {
        return cost;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
