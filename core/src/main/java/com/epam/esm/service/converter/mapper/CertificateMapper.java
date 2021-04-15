package com.epam.esm.service.converter.mapper;

import com.epam.esm.model.Certificate;
import com.epam.esm.model.Currency;
import com.epam.esm.model.Price;
import com.epam.esm.model.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CertificateMapper implements RowMapper<Certificate> {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Certificate certificate = new Certificate();
        certificate.setId(rs.getLong("id"));
        certificate.setName(rs.getString("name"));
        certificate.setDescription(rs.getString("description"));
        certificate.setPrice(new Price(rs.getBigDecimal("cost"),
                Currency.getById(rs.getInt("currency"))));
        certificate.setDuration(Duration.ofDays(rs.getLong("duration")));
        certificate.setDateOfCreation(rs.getTimestamp("create_date").toLocalDateTime());
        certificate.setDateOfModification(rs.getTimestamp("last_update_date").toLocalDateTime());
        return certificate;
    }
}

