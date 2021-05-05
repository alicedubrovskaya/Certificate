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

public class CertificateDtoConverter implements DtoConverter<Certificate, CertificateDto> {
    private final DtoConverter<Tag, TagDto> tagDtoConverter;

    public CertificateDtoConverter() {
        this.tagDtoConverter = new TagDtoConverter();
    }

    @Override
    public CertificateDto convertToDto(Certificate certificate) {
        List<TagDto> tagDtos = new ArrayList<>();
        if (certificate.getTags() != null) {
            tagDtos = certificate.getTags().stream()
                    .map(tagDtoConverter::convertToDto)
                    .collect(Collectors.toList());
        }
        CertificateDto certificateDto = CertificateDto.builder()
                .id(certificate.getId())
                .dateOfCreation(certificate.getDateOfCreation())
                .dateOfModification(certificate.getDateOfModification())
                .duration(certificate.getDuration())
                .name(certificate.getName())
                .description(certificate.getDescription())
                .tags(tagDtos)
                .build();
        if (certificate.getPrice() != null) {
            certificateDto.setPrice(
                    new PriceDto(certificate.getPrice().getCost(), certificate.getPrice().getCurrency()));
        }
        return certificateDto;
    }

    @Override
    public Certificate convertToEntity(CertificateDto certificateDto, Certificate certificate) {
        List<Tag> tags = new ArrayList<>();
        if (certificateDto.getTags() != null) {
            tags = certificateDto.getTags().stream()
                    .map(tagDto -> tagDtoConverter.convertToEntity(tagDto, new Tag()))
                    .collect(Collectors.toList());
        }
        certificate.setId(certificateDto.getId());
        certificate.setDateOfCreation(certificateDto.getDateOfCreation());
        certificate.setDateOfModification(certificateDto.getDateOfModification());
        certificate.setDuration(certificateDto.getDuration());
        certificate.setName(certificateDto.getName());
        certificate.setDescription(certificateDto.getDescription());
        if (certificateDto.getPrice() != null) {
            certificate.setPrice(new Price(certificateDto.getPrice().getCost(), certificateDto.getPrice().getCurrency()));
        }
        certificate.setTags(tags);
        return certificate;
    }
}
