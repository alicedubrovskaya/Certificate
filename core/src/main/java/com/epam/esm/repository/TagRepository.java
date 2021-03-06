package com.epam.esm.repository;

import com.epam.esm.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    Tag create(Tag tag);

    Tag createOrGet(Tag tag);

    Optional<Tag> findById(Long id);

    Optional<Tag> findByName(String name);

    List<Tag> findByCertificateId(Long certificateId);

    List<Tag> isIncludedInCertificate(Long certificateId, Long tagId);

    List<Tag> findAll();

    boolean delete(Long id);
}
