package com.epam.esm.service.dto;

import com.epam.esm.model.Currency;
import com.epam.esm.model.Price;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

