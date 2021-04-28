package com.epam.esm.service.converter;

import com.epam.esm.model.Tag;
import com.epam.esm.service.dto.TagDto;
import org.springframework.stereotype.Component;

@Component
public class TagDtoConverter implements DtoConverter<Tag, TagDto> {
    @Override
    public TagDto convert(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

    @Override
    public Tag unconvert(TagDto tagDto) {
        return Tag.builder()
                .id(tagDto.getId())
                .name(tagDto.getName())
                .build();
    }
}
