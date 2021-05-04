package com.epam.esm.repository.impl;

import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;

    private static final String CREATE_QUERY = "INSERT INTO tag (name) VALUES (?)";

    private static final String READ_BY_ID_QUERY = "SELECT id, name FROM tag WHERE id = ?";

    private static final String READ_BY_NAME_QUERY = "SELECT id, name FROM tag WHERE name = ?";

    private static final String READ_BY_CERTIFICATE_ID_QUERY = "SELECT id, name FROM tag JOIN tag_gift_certificate tgc " +
            "ON tag.id = tgc.tag_id WHERE gift_certificate_id = ?";

    private static final String READ_BY_CERTIFICATE_ID_AND_TAG_ID_QUERY = "SELECT id, name FROM tag JOIN tag_gift_certificate tgc " +
            "ON tag.id = tgc.tag_id WHERE gift_certificate_id = ? AND tag_id=?";

    private static final String READ_QUERY = "SELECT id, name FROM tag";

    private static final String DELETE_QUERY = "DELETE FROM tag WHERE id = ?";

    @Autowired
    protected TagRepositoryImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagMapper = tagMapper;
    }

    @Override
    public Tag create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, tag.getName());
            return preparedStatement;
        }, keyHolder);
        if (keyHolder.getKeys().size() > 1) {
            Object integer = keyHolder.getKeys().get("id");
            tag.setId(Long.valueOf(integer.toString()));
        } else {
            tag.setId(keyHolder.getKey().longValue());
        }
        return tag;
    }

    @Override
    public Tag createOrGet(Tag tag) {
        Optional<Tag> tagOptional = findByName(tag.getName());
        return tagOptional.orElseGet(() -> create(tag));
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return jdbcTemplate.query(READ_BY_ID_QUERY, tagMapper, id).stream().findAny();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(READ_BY_NAME_QUERY, tagMapper, name).stream().findAny();
    }

    @Override
    public List<Tag> findByCertificateId(Long certificateId) {
        return jdbcTemplate.query(READ_BY_CERTIFICATE_ID_QUERY, tagMapper, certificateId);
    }

    @Override
    public List<Tag> isIncludedInCertificate(Long certificateId, Long tagId) {
        return jdbcTemplate.query(READ_BY_CERTIFICATE_ID_AND_TAG_ID_QUERY, tagMapper, certificateId, tagId);
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(READ_QUERY, tagMapper);
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_QUERY, id) != 0;
    }
}
