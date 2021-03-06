package com.epam.esm.service.service.impl;

import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.model.Tag;
import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.model.enumeration.RequestedResource;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.converter.DtoConverter;
import com.epam.esm.service.converter.TagDtoConverter;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.service.TagService;
import com.epam.esm.service.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final Validator<TagDto> tagValidator;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository,
                          Validator<TagDto> tagValidator) {
        this.tagRepository = tagRepository;
        this.tagValidator = tagValidator;
    }

    @Override
    public TagDto create(TagDto tagDto) {
        tagValidator.validate(tagDto);
        if (!tagValidator.getMessages().isEmpty()) {
            throw new ValidationException(tagValidator.getMessages(), RequestedResource.TAG);
        }

        if (tagRepository.findByName(tagDto.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException(ErrorMessage.RESOURCE_ALREADY_EXISTS, RequestedResource.TAG);
        }

        DtoConverter<Tag, TagDto> tagConverter = new TagDtoConverter();
        Tag createdTag = tagRepository.create(tagConverter.convertToEntity(tagDto, new Tag()));
        return tagConverter.convertToDto(createdTag);
    }

    @Override
    public void delete(Long tagId) {
        tagRepository.findById(tagId).ifPresentOrElse(t -> tagRepository.delete(tagId), () -> {
            throw new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND, RequestedResource.TAG,
                    tagId);
        });
    }

    @Override
    public TagDto findById(Long tagId) {
        DtoConverter<Tag, TagDto> tagConverter = new TagDtoConverter();
        return tagConverter.convertToDto(tagRepository.findById(tagId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND, RequestedResource.TAG, tagId)));
    }

    @Override
    public List<TagDto> findAll() {
        DtoConverter<Tag, TagDto> tagConverter = new TagDtoConverter();
        return tagRepository.findAll().stream()
                .map(tagConverter::convertToDto)
                .collect(Collectors.toList());
    }
}
