package com.epam.esm.repository.impl;

import com.epam.esm.model.Certificate;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.service.converter.mapper.CertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CertificateMapper certificateMapper;

    private static final String CREATE_CERTIFICATE =
            "INSERT INTO `gift_certificate` (name, description, cost, currency, duration, create_date, last_update_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?);";

    private static final String ATTACH_TAG = "INSERT INTO `tag_gift_certificate` (tag_id, gift_certificate_id)" +
            " VALUES (?,?)";

    @Autowired
    protected CertificateRepositoryImpl(JdbcTemplate jdbcTemplate, CertificateMapper certificateMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.certificateMapper = certificateMapper;
    }

    @Override
    public Certificate create(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            preparedStatement.setString(index++, certificate.getName());
            preparedStatement.setString(index++, certificate.getDescription());
            preparedStatement.setBigDecimal(index++, certificate.getPrice().getCost());
            preparedStatement.setInt(index++, certificate.getPrice().getCurrency().getId());
            preparedStatement.setLong(index++, certificate.getDuration().toDays());
            preparedStatement.setTimestamp(index++, Timestamp.valueOf(certificate.getDateOfCreation()));
            preparedStatement.setTimestamp(index, Timestamp.valueOf(certificate.getDateOfModification()));
            return preparedStatement;
        }, keyHolder);
        certificate.setId(keyHolder.getKey().longValue());
        return certificate;
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        //TODO Duration.ofDays(int)
        return Optional.empty();
    }

    @Override
    public List<Certificate> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void attachTagToCertificate(Long certificateId, Long tagId) {
        jdbcTemplate.update(ATTACH_TAG, tagId, certificateId);
    }
}
