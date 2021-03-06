package com.epam.esm.repository.impl;

import com.epam.esm.model.Certificate;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.mapper.CertificateMapper;
import com.epam.esm.repository.specification.CertificateSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CertificateMapper certificateMapper;

    private static final String CREATE_QUERY =
            "INSERT INTO gift_certificate (name, description, cost, currency, duration, create_date, last_update_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String READ_QUERY = "SELECT id, name, description, cost, currency, duration, create_date, last_update_date" +
            " FROM gift_certificate WHERE id=?";

    private static final String UPDATE_QUERY = "UPDATE gift_certificate SET name=?,description=?, cost=?," +
            " currency=?, duration=?, last_update_date=? WHERE id=?";

    private static final String ATTACH_TAG_QUERY = "INSERT INTO tag_gift_certificate (tag_id, gift_certificate_id)" +
            " VALUES (?,?)";

    private static final String DETACH_TAG_QUERY = "DELETE FROM tag_gift_certificate WHERE gift_certificate_id=?";

    private static final String DELETE_QUERY = "DELETE FROM gift_certificate WHERE id=? ";

    @Autowired
    protected CertificateRepositoryImpl(JdbcTemplate jdbcTemplate, CertificateMapper certificateMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.certificateMapper = certificateMapper;
    }

    @Override
    public Certificate create(Certificate certificate) {
        certificate.setDateOfCreation(LocalDateTime.now());
        certificate.setDateOfModification(LocalDateTime.now());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            preparedStatement.setString(index++, certificate.getName());
            preparedStatement.setString(index++, certificate.getDescription());
            preparedStatement.setBigDecimal(index++, certificate.getPrice().getCost());
            preparedStatement.setInt(index++, certificate.getPrice().getCurrency().getId());
            preparedStatement.setLong(index++, certificate.getDuration().toDays());
            preparedStatement.setTimestamp(index++, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setTimestamp(index, Timestamp.valueOf(LocalDateTime.now()));
            return preparedStatement;
        }, keyHolder);
        if (keyHolder.getKeys().size() > 1) {
            Object integer = keyHolder.getKeys().get("id");
            certificate.setId(Long.valueOf(integer.toString()));
        } else {
            certificate.setId(keyHolder.getKey().longValue());
        }
        return certificate;
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        return jdbcTemplate.query(READ_QUERY, certificateMapper, id).stream().findAny();
    }

    @Override
    public List<Certificate> findAll(CertificateSpecification certificateSpecification) {
        return jdbcTemplate.query(certificateSpecification.buildSql(), certificateMapper);
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_QUERY, id) != 0;
    }

    @Override
    public void update(Certificate certificate) {
        jdbcTemplate.update(UPDATE_QUERY, certificate.getName(), certificate.getDescription(),
                certificate.getPrice().getCost(), certificate.getPrice().getCurrency().getId(),
                certificate.getDuration().toDays(), Timestamp.valueOf(LocalDateTime.now()),
                certificate.getId());
    }

    @Override
    public boolean attachTagToCertificate(Long certificateId, Long tagId) {
        return jdbcTemplate.update(ATTACH_TAG_QUERY, tagId, certificateId) != 0;
    }

    @Override
    public boolean detachTagsFromCertificate(Long certificateId) {
        return jdbcTemplate.update(DETACH_TAG_QUERY, certificateId) != 0;
    }
}
