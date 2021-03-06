package com.epam.esm.model;

import com.epam.esm.model.enumeration.Currency;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class Price {
    private BigDecimal cost;
    private Currency currency;

    public BigDecimal getCost() {
        return cost;
    }

    public Currency getCurrency() {
        return currency;
    }
}
