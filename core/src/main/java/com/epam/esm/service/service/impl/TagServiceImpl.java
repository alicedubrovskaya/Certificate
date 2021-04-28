package com.epam.esm.service.service.impl;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.model.Tag;
import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.model.enumeration.RequestedResource;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.converter.DtoConverter;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.service.TagService;
import com.epam.esm.service.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final DtoConverter<Tag, TagDto> dtoConverter;
    private final TagRepository tagRepository;
    private final Validator<TagDto> tagValidator;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, DtoConverter<Tag, TagDto> dtoConverter,
                          Validator<TagDto> tagValidator) {
        this.tagRepository = tagRepository;
        this.dtoConverter = dtoConverter;
        this.tagValidator = tagValidator;
    }

    @Override
    public TagDto create(TagDto tagDto) throws ValidationException {
        tagValidator.validate(tagDto);
        if (!tagValidator.getMessages().isEmpty()) {
            throw new ValidationException(tagValidator.getMessages(), RequestedResource.TAG);
        }

        Tag tag = dtoConverter.unconvert(tagDto);
        Tag createdTag = tagRepository.create(tag);
        return dtoConverter.convert(createdTag);
    }

    @Override
    public TagDto update(TagDto tagDto) {
        throw new UnsupportedOperationException("Update operation is not permitted");
    }

    @Override
    public void delete(Long tagId) {
        tagRepository.findById(tagId).ifPresentOrElse(t -> tagRepository.delete(tagId), () -> {
            throw new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND, RequestedResource.TAG);
        });
    }

    @Override
    public TagDto findById(Long tagId) {
        return dtoConverter.convert(tagRepository.findById(tagId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND,RequestedResource.TAG)));
    }

    @Override
    public List<TagDto> findAll() {
        return tagRepository.findAll().stream()
                .map(dtoConverter::convert)
                .collect(Collectors.toList());
    }
}
