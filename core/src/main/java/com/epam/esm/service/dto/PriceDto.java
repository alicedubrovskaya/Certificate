package com.epam.esm.service.dto;

import com.epam.esm.model.enumeration.Currency;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
public class PriceDto {
    private BigDecimal cost;

    private Currency currency;

    public BigDecimal getCost() {
        return cost;
    }

    public Currency getCurrency() {
        return currency;
    }
}

