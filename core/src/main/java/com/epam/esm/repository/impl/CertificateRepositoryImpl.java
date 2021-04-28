package com.epam.esm.repository.impl;

import com.epam.esm.model.Certificate;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.mapper.CertificateMapper;
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

    private static final String READ_CERTIFICATE = "SELECT id, name, description, cost, currency, duration, create_date, last_update_date" +
            " FROM gift_certificate WHERE id=?";

    private static final String READ_CERTIFICATES_BASE = "SELECT id, name, description, cost, currency, duration, create_date, last_update_date" +
            " FROM gift_certificate WHERE TRUE ";

    private static final String UPDATE_CERTIFICATE = "UPDATE `gift_certificate` SET name=?,description=?, cost=?," +
            " currency=?, duration=?, last_update_date=? WHERE id=?";

    private static final String ATTACH_TAG = "INSERT INTO `tag_gift_certificate` (tag_id, gift_certificate_id)" +
            " VALUES (?,?)";

    private static final String DETACH_TAG = "DELETE FROM `tag_gift_certificate` WHERE gift_certificate_id=?";

    private static final String DELETE_CERTIFICATE = "DELETE FROM `gift_certificate` WHERE `id`=? ";

    private static final String TAG_NAME_FILTER = "AND id IN (SELECT gift_certificate_id FROM tag_gift_certificate JOIN tag " +
            "ON tag.id=tag_gift_certificate.tag_id WHERE tag.name=";

    private static final String DESCRIPTION_FILTER = "AND description LIKE '%";

    private static final String NAME_FILTER = "AND name LIKE '%";

    private static final String DESC = "DESC";

    private static final String ORDER_BY = "ORDER BY ";

    private static final String AND = "";



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
        return jdbcTemplate.query(READ_CERTIFICATE, certificateMapper, id).stream().findAny();
    }


    @Override
    public List<Certificate> findAll(String tagName, String searchByName, String searchByDescription, String sortBy, String sortOrder) {
        StringBuilder sql = new StringBuilder(READ_CERTIFICATES_BASE);
        if (tagName != null) {
            sql.append(TAG_NAME_FILTER)
                    .append(tagName)
                    .append(") ");
        }
        if (searchByName != null) {
            sql.append(NAME_FILTER)
                    .append(searchByName)
                    .append("%' ");
        }
        if (searchByDescription != null) {
            sql.append(DESCRIPTION_FILTER)
                    .append(searchByDescription)
                    .append("%'  ");
        }
        if (sortBy != null) {
            sql.append(ORDER_BY)
                    .append(sortBy);
            if (DESC.equals(sortOrder)) {
                sql.append(" " + DESC);
            }
        }
        return jdbcTemplate.query(sql.toString(), certificateMapper);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_CERTIFICATE, id);
    }

    @Override
    public void update(Certificate certificate) {
        jdbcTemplate.update(UPDATE_CERTIFICATE, certificate.getName(), certificate.getDescription(),
                certificate.getPrice().getCost(), certificate.getPrice().getCurrency().getId(),
                certificate.getDuration().toDays(), Timestamp.valueOf(certificate.getDateOfModification()),
                certificate.getId());
    }

    @Override
    public void attachTagToCertificate(Long certificateId, Long tagId) {
        jdbcTemplate.update(ATTACH_TAG, tagId, certificateId);
    }

    @Override
    public void detachTagsFromCertificate(Long certificateId) {
        jdbcTemplate.update(DETACH_TAG, certificateId);
    }
}
