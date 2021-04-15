package com.epam.esm.service.service;

import com.epam.esm.service.dto.TagDto;

import java.util.List;

public interface TagService extends CrudService<TagDto> {
    List<TagDto> read();
 }
