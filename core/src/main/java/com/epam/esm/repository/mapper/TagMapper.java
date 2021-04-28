package com.epam.esm.repository.mapper;

import com.epam.esm.model.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Tag.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}

