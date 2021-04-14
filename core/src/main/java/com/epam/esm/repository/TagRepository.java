package com.epam.esm.repository;

import com.epam.esm.model.Tag;

import java.util.Optional;

public interface TagRepository {
    Tag create(Tag tag);

    Optional<Tag> findById(Long id);

    void delete(Long id);
}
