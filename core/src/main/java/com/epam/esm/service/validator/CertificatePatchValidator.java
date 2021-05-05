package com.epam.esm.service.validator;

import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.PriceDto;
import com.epam.esm.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Qualifier("validator_patch")
public class CertificatePatchValidator implements Validator<CertificateDto> {
    private final Validator<TagDto> tagValidator;
    private final List<ErrorMessage> errors = new ArrayList<>();
    private static final String COST_CORRECT = "\\d{1,10}\\.\\d{0,4}";
    private static final String COST_COUNT_OF_CHARACTERS_CORRECT = ".{2,10}";

    @Autowired
    public CertificatePatchValidator(Validator<TagDto> tagValidator) {
        this.tagValidator = tagValidator;
    }

    @Override
    public void validate(CertificateDto certificateDto) {
        validateDto(certificateDto);
        if (errors.isEmpty()) {
            validateName(certificateDto.getName());
            validateDescription(certificateDto.getDescription());
            validatePrice(certificateDto.getPrice());
            validateDuration(certificateDto.getDuration());
            validateTags(certificateDto.getTags());
        }
    }

    protected void validateName(String name) {
        if (name != null && !(name.length() > 5 && name.length() < 255)) {
            errors.add(ErrorMessage.CERTIFICATE_NAME_INCORRECT);
        }
    }

    protected void validateDto(CertificateDto certificateDto) {
        if (certificateDto == null) {
            errors.add(ErrorMessage.CERTIFICATE_DTO_EMPTY);
        }
    }

    protected void validateDescription(String description) {
        if (description != null && !(description.length() >= 10 && description.length() <= 400)) {
            errors.add(ErrorMessage.CERTIFICATE_DESCRIPTION_INCORRECT);
        }
    }

    protected void validatePrice(PriceDto priceDto) {
        if (priceDto != null && (priceDto.getCost() == null || priceDto.getCurrency() == null)) {
            errors.add(ErrorMessage.CERTIFICATE_PRICE_FIELD_EMPTY);
        }

        if ((priceDto != null && priceDto.getCost() != null && priceDto.getCurrency() != null) &&
                (!priceDto.getCost().toString().matches(COST_CORRECT) ||
                        !priceDto.getCost().toString().matches(COST_COUNT_OF_CHARACTERS_CORRECT)
                )
        ) {
            errors.add(ErrorMessage.CERTIFICATE_PRICE_COST_INCORRECT);
        }
    }

    protected void validateDuration(Duration duration) {
        if (duration != null && duration.toDays() <= 0) {
            errors.add(ErrorMessage.CERTIFICATE_DURATION_INCORRECT);
        }
    }

    protected void validateTags(List<TagDto> tags) {
        if (tags != null) {
            tags.forEach(tagValidator::validate);
            errors.addAll(tagValidator.getMessages());
        }
    }

    @Override
    public List<ErrorMessage> getMessages() {
        return errors;
    }
}
