package com.epam.esm.service.service;

import com.epam.esm.exception.ValidationException;
import com.epam.esm.service.dto.TagDto;

import java.util.List;

public interface TagService {
    TagDto create(TagDto tagDto) throws ValidationException;

    void delete(Long tagId);

    TagDto findById(Long tagId);

    List<TagDto> findAll();
}
