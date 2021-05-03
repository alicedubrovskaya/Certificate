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
import com.epam.esm.service.converter.CertificateDtoConverter;
import com.epam.esm.service.converter.CertificatePatchDtoConverter;
import com.epam.esm.service.converter.DtoConverter;
import com.epam.esm.service.converter.TagDtoConverter;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchCertificateDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.service.CertificateService;
import com.epam.esm.service.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final Validator<CertificateDto> certificateDtoValidator;
    private final Validator<SearchCertificateDto> searchCertificateDtoValidator;
    private final Validator<CertificateDto> certificatePatchValidator;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository,
                                  TagRepository tagRepository,
                                  @Qualifier("validator") Validator<CertificateDto> certificateDtoValidator,
                                  @Qualifier("validator_patch") Validator<CertificateDto> certificatePatchValidator,
                                  Validator<SearchCertificateDto> searchCertificateDtoValidator
    ) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.certificateDtoValidator = certificateDtoValidator;
        this.certificatePatchValidator = certificatePatchValidator;
        this.searchCertificateDtoValidator = searchCertificateDtoValidator;
    }

    @Transactional
    @Override
    public CertificateDto create(CertificateDto certificateDto) {
        certificateDtoValidator.validate(certificateDto);
        if (!certificateDtoValidator.getMessages().isEmpty()) {
            throw new ValidationException(certificateDtoValidator.getMessages(), RequestedResource.CERTIFICATE);
        }

        DtoConverter<Certificate, CertificateDto> certificateConverter = new CertificateDtoConverter();
        Certificate createdCertificate = certificateRepository.create(
                certificateConverter.convertToEntity(certificateDto, new Certificate()));

        CertificateDto createdCertificateDto = certificateConverter.convertToDto(createdCertificate);
        createdCertificateDto.setTags(attachTagsToCertificate(createdCertificate.getId(), certificateDto.getTags()));
        return certificateConverter.convertToDto(createdCertificate);
    }


    @Transactional
    @Override
    public CertificateDto update(CertificateDto certificateDto) {
        certificateDtoValidator.validate(certificateDto);
        if (!certificateDtoValidator.getMessages().isEmpty()) {
            throw new ValidationException(certificateDtoValidator.getMessages(), RequestedResource.CERTIFICATE);
        }

        if (certificateRepository.findById(certificateDto.getId()).isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND,
                    RequestedResource.CERTIFICATE, certificateDto.getId());
        }

        DtoConverter<Certificate, CertificateDto> certificateConverter = new CertificateDtoConverter();
        Certificate certificateForUpdate = certificateConverter.convertToEntity(certificateDto, new Certificate());
        certificateRepository.update(certificateForUpdate);
        certificateRepository.detachTagsFromCertificate(certificateDto.getId());
        List<TagDto> attachedTags = attachTagsToCertificate(certificateDto.getId(), certificateDto.getTags());
        DtoConverter<Tag, TagDto> tagConverter = new TagDtoConverter();
        certificateForUpdate.setTags(
                attachedTags.stream()
                        .map(tagDto -> tagConverter.convertToEntity(tagDto, new Tag()))
                        .collect(Collectors.toList()));
        return certificateConverter.convertToDto(certificateForUpdate);
    }

    @Transactional
    @Override
    public CertificateDto patch(CertificateDto certificateDto) {
        certificatePatchValidator.validate(certificateDto);
        if (!certificatePatchValidator.getMessages().isEmpty()) {
            throw new ValidationException(certificatePatchValidator.getMessages(), RequestedResource.CERTIFICATE);
        }

        Certificate existingCertificate = certificateRepository
                .findById(certificateDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND,
                        RequestedResource.CERTIFICATE, certificateDto.getId()));

        CertificatePatchDtoConverter converter = new CertificatePatchDtoConverter();
        Certificate certificateForUpdate = converter.convertToEntity(certificateDto, existingCertificate);
        certificateRepository.update(certificateForUpdate);

        DtoConverter<Tag, TagDto> tagConverter = new TagDtoConverter();
        if (!certificateDto.getTags().isEmpty()) {
            List<TagDto> attachedTags = attachTagsToCertificate(certificateForUpdate.getId(), certificateDto.getTags());
            certificateForUpdate.setTags(
                    attachedTags.stream()
                            .map(tagDto -> tagConverter.convertToEntity(tagDto, new Tag()))
                            .collect(Collectors.toList()));
        }
        DtoConverter<Certificate, CertificateDto> certificateConverter = new CertificateDtoConverter();
        return certificateConverter.convertToDto(certificateForUpdate);
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
        certificate.setTags(tagRepository.findByCertificateId(certificateId));
        DtoConverter<Certificate, CertificateDto> certificateConverter = new CertificateDtoConverter();
        return certificateConverter.convertToDto(certificate);
    }

    @Override
    public List<CertificateDto> findAllByParams(SearchCertificateDto searchCertificateDto) {
        searchCertificateDtoValidator.validate(searchCertificateDto);
        if (!searchCertificateDtoValidator.getMessages().isEmpty()) {
            throw new ValidationException(searchCertificateDtoValidator.getMessages(), RequestedResource.CERTIFICATE);
        }

        List<Certificate> certificates = certificateRepository.findAll(new CertificateSpecification(searchCertificateDto));
        certificates.forEach(certificate -> certificate.setTags(
                tagRepository.findByCertificateId(certificate.getId())));
        DtoConverter<Certificate, CertificateDto> certificateConverter = new CertificateDtoConverter();
        return certificates.stream()
                .map(certificateConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TagDto> attachTagsToCertificate(Long certificateId, List<TagDto> tags) {
        DtoConverter<Tag, TagDto> tagConverter = new TagDtoConverter();
        tags.forEach(tag -> {
            tag.setId(tagRepository.createOrGet(tagConverter.convertToEntity(tag, new Tag())).getId());
            if (tagRepository.isIncludedInCertificate(certificateId, tag.getId())
                    .stream().findAny()
                    .isEmpty()) {
                certificateRepository.attachTagToCertificate(certificateId, tag.getId());
            }
        });
        return tags;
    }
}
