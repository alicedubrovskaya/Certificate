package com.epam.esm.repository.mapper;

import com.epam.esm.model.Certificate;
import com.epam.esm.model.enumeration.Currency;
import com.epam.esm.model.Price;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

@Component
public class CertificateMapper implements RowMapper<Certificate> {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Certificate.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .price(new Price(rs.getBigDecimal("cost"),
                        Currency.getById(rs.getInt("currency"))))
                .duration(Duration.ofDays(rs.getLong("duration")))
                .dateOfCreation(rs.getTimestamp("create_date").toLocalDateTime())
                .dateOfModification(rs.getTimestamp("last_update_date").toLocalDateTime())
                .build();
    }
}

