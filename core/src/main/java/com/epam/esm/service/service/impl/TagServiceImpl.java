package com.epam.esm.service.service.impl;

import com.epam.esm.exception.ErrorMessage;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.converter.DtoConverter;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final DtoConverter<Tag, TagDto> dtoConverter;
    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, DtoConverter<Tag, TagDto> dtoConverter) {
        this.tagRepository = tagRepository;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public TagDto create(TagDto entity) {
        Tag tag = dtoConverter.unconvert(entity);
        Tag createdTag = tagRepository.create(tag);
        return dtoConverter.convert(createdTag);
    }

    @Override
    public TagDto update(TagDto entity) {
        throw new UnsupportedOperationException("Update operation is not permitted");
    }

    @Override
    public void delete(Long id) {
        tagRepository.findById(id).ifPresentOrElse(t -> tagRepository.delete(id), () -> {
            throw new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, id));
        });
    }

    @Override
    public TagDto findById(Long id) {
        return dtoConverter.convert(tagRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, id))));
    }

    @Override
    public List<TagDto> read() {
        return tagRepository.findAll().stream()
                .map(dtoConverter::convert)
                .collect(Collectors.toList());
    }
}
