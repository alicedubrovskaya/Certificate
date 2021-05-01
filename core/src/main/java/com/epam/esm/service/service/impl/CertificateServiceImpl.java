package com.epam.esm.service.service.impl;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.model.enumeration.RequestedResource;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.specification.CertificateSpecification;
import com.epam.esm.service.converter.DtoConverter;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchCertificateDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.service.CertificateService;
import com.epam.esm.service.converter.mapper.Mapper;
import com.epam.esm.service.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final DtoConverter<Certificate, CertificateDto> certificateConverter;
    private final DtoConverter<Tag, TagDto> tagConverter;
    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final Validator<CertificateDto> certificateDtoValidator;
    private final Validator<SearchCertificateDto> searchCertificateDtoValidator;

    private final Mapper mapper;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository,
                                  TagRepository tagRepository,
                                  DtoConverter<Certificate, CertificateDto> certificateConverter,
                                  DtoConverter<Tag, TagDto> tagConverter,
                                  Validator<CertificateDto> certificateDtoValidator,
                                  Validator<SearchCertificateDto> searchCertificateDtoValidator,
                                  Mapper mapper) {

        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.certificateConverter = certificateConverter;
        this.tagConverter = tagConverter;
        this.certificateDtoValidator = certificateDtoValidator;
        this.searchCertificateDtoValidator = searchCertificateDtoValidator;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public CertificateDto create(CertificateDto certificateDto) throws ValidationException {
        certificateDtoValidator.validate(certificateDto);
        if (!certificateDtoValidator.getMessages().isEmpty()) {
            throw new ValidationException(certificateDtoValidator.getMessages(), RequestedResource.CERTIFICATE);
        }

        certificateDto.setDateOfCreation(LocalDateTime.now());
        certificateDto.setDateOfModification(LocalDateTime.now());
        Certificate createdCertificate = certificateRepository.create(certificateConverter.unconvert(certificateDto));

        CertificateDto createdCertificateDto = certificateConverter.convert(createdCertificate);
        createdCertificateDto.setTags(attachTagsToCertificate(createdCertificate.getId(), certificateDto.getTags()));
        return certificateConverter.convert(createdCertificate);
    }

    @Transactional
    @Override
    public CertificateDto update(CertificateDto certificateDto) {
        //TODO validation for certDto with @Valid (or with validator, but without checking name for null)
        Certificate existingCertificate = certificateRepository
                .findById(certificateDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND,
                        RequestedResource.CERTIFICATE, certificateDto.getId()));

        Certificate certificateForUpdate = mapper.map(certificateDto, existingCertificate);
        certificateRepository.update(certificateForUpdate);

        if (!certificateDto.getTags().isEmpty()) {
            certificateRepository.detachTagsFromCertificate(certificateForUpdate.getId());
            Set<TagDto> attachedTags = attachTagsToCertificate(certificateForUpdate.getId(), certificateDto.getTags());
            certificateForUpdate.setTags(
                    attachedTags.stream().map(tagConverter::unconvert).collect(Collectors.toSet()));
        }
        return certificateConverter.convert(certificateForUpdate);
    }

    @Override
    public void delete(Long certificateId) {
        certificateRepository.findById(certificateId).ifPresentOrElse(certificate -> certificateRepository.delete(certificateId), () -> {
            throw new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND, RequestedResource.CERTIFICATE,
                    certificateId);
        });
    }

    @Override
    public CertificateDto findById(Long certificateId) {
        Certificate certificate = certificateRepository.findById(certificateId).orElseThrow(() -> {
            throw new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND, RequestedResource.CERTIFICATE,
                    certificateId);
        });
        certificate.setTags(new HashSet<>(tagRepository.findByCertificateId(certificateId)));
        return certificateConverter.convert(certificate);
    }

    @Override
    public List<CertificateDto> findAllByParams(SearchCertificateDto searchCertificateDto) throws ValidationException {
        searchCertificateDtoValidator.validate(searchCertificateDto);
        if (!searchCertificateDtoValidator.getMessages().isEmpty()) {
            throw new ValidationException(searchCertificateDtoValidator.getMessages(), RequestedResource.CERTIFICATE);
        }

        List<Certificate> certificates = certificateRepository.findAll(new CertificateSpecification(searchCertificateDto));
        certificates.forEach(certificate -> certificate.setTags(
                new HashSet<>(tagRepository.findByCertificateId(certificate.getId()))));
        return certificates.stream()
                .map(certificateConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Set<TagDto> attachTagsToCertificate(Long certificateId, Set<TagDto> tags) {
        tags.forEach(tag -> {
            Optional<Tag> tagOptional = tagRepository.findByName(tag.getName());
            if (tagOptional.isEmpty()) {
                Tag createdTag = tagRepository.create(tagConverter.unconvert(tag));
                tag.setId(createdTag.getId());
            } else {
                Tag existingTag = tagOptional.get();
                tag.setId(existingTag.getId());
            }
            certificateRepository.attachTagToCertificate(certificateId, tag.getId());
        });
        return tags;
    }
}
