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

    private static final String CREATE_TAG = "INSERT INTO tag (name) VALUES (?)";

    private static final String READ_TAG_BY_ID = "SELECT id, name FROM tag WHERE id = ?";

    private static final String READ_TAG_BY_NAME = "SELECT `id`, `name` FROM `tag` WHERE `name` = ?";

    private static final String READ_TAG_BY_CERTIFICATE_ID = "SELECT `id`, `name` FROM `tag` JOIN tag_gift_certificate tgc " +
            "ON tag.id = tgc.tag_id WHERE `gift_certificate_id` = ?";

    private static final String READ_TAGS = "SELECT `id`, `name` FROM `tag`";

    private static final String DELETE_TAG = "DELETE FROM `tag` WHERE `id` = ?";

    @Autowired
    protected TagRepositoryImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagMapper = tagMapper;
    }

    @Override
    public Tag create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TAG, Statement.RETURN_GENERATED_KEYS);
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
    public Optional<Tag> findById(Long id) {
        return jdbcTemplate.query(READ_TAG_BY_ID, tagMapper, id).stream().findAny();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(READ_TAG_BY_NAME, tagMapper, name).stream().findAny();
    }

    @Override
    public List<Tag> findByCertificateId(Long certificateId) {
        return jdbcTemplate.query(READ_TAG_BY_CERTIFICATE_ID, tagMapper, certificateId);
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(READ_TAGS, tagMapper);
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_TAG, id) != 0;
    }
}
