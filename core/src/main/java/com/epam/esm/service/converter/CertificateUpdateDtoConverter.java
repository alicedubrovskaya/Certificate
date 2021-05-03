package com.epam.esm.service.converter;

import com.epam.esm.model.Certificate;
import com.epam.esm.model.Price;
import com.epam.esm.model.Tag;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.PriceDto;
import com.epam.esm.service.dto.TagDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CertificateUpdateDtoConverter {
    private final TagDtoConverter tagDtoConverter;

    public CertificateUpdateDtoConverter() {
        this.tagDtoConverter = new TagDtoConverter();
    }

    public CertificateDto convertToDto(Certificate certificate) {
        List<TagDto> tagDtos = new ArrayList<>();
        if (certificate.getTags() != null) {
            tagDtos = certificate.getTags().stream()
                    .map(tagDtoConverter::convertToDto)
                    .collect(Collectors.toList());
        }
        return CertificateDto.builder()
                .id(certificate.getId())
                .dateOfCreation(certificate.getDateOfCreation())
                .dateOfModification(certificate.getDateOfModification())
                .duration(certificate.getDuration())
                .name(certificate.getName())
                .description(certificate.getDescription())
                .price(new PriceDto(certificate.getPrice().getCost(), certificate.getPrice().getCurrency()))
                .tags(tagDtos)
                .build();
    }

    public Certificate convertToEntity(CertificateDto certificateDto) {
        List<Tag> tags = new ArrayList<>();
        if (certificateDto.getTags() != null) {
            tags = certificateDto.getTags().stream()
                    .map(tagDtoConverter::convertToEntity)
                    .collect(Collectors.toList());
        }
        return Certificate.builder()
                .id(certificateDto.getId())
                .dateOfCreation(certificateDto.getDateOfCreation())
                .dateOfModification(certificateDto.getDateOfModification())
                .duration(certificateDto.getDuration())
                .name(certificateDto.getName())
                .description(certificateDto.getDescription())
                .price(new Price(certificateDto.getPrice().getCost(), certificateDto.getPrice().getCurrency()))
                .tags(tags)
                .build();
    }
}
