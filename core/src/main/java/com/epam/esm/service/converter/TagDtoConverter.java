package com.epam.esm.service.converter;

import com.epam.esm.model.Tag;
import com.epam.esm.service.dto.TagDto;

public class TagDtoConverter {
    public TagDto convertToDto(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

    public Tag convertToEntity(TagDto tagDto) {
        return Tag.builder()
                .id(tagDto.getId())
                .name(tagDto.getName())
                .build();
    }
}
