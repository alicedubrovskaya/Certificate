package com.epam.esm.service.validator;

import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.service.dto.SearchCertificateDto;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SearchCertificateValidator implements Validator<SearchCertificateDto> {
    private final List<ErrorMessage> errors = new ArrayList<>();
    private static final List<String> CORRECT_FIELDS_TO_SORT_BY = Arrays.asList("id", "name", "description", "cost",
            "currency", "duration", "create_date", "last_update_date");

    @Override
    public void validate(SearchCertificateDto searchCertificateDto) {
        validateDto(searchCertificateDto);
        if (errors.isEmpty()) {
            validateFieldsToSortBy(searchCertificateDto);
        }
    }

    protected void validateDto(SearchCertificateDto searchCertificateDto) {
        if (searchCertificateDto == null) {
            errors.add(ErrorMessage.SEARCH_CERTIFICATE_DTO_EMPTY);
        }
    }

    protected void validateFieldsToSortBy(SearchCertificateDto searchCertificateDto) {
        List<String> fieldToSortBy = searchCertificateDto.getFieldsToSortBy();
        if (fieldToSortBy != null && fieldToSortBy.stream().anyMatch(field -> !CORRECT_FIELDS_TO_SORT_BY.contains(field))) {
            errors.add(ErrorMessage.SEARCH_CERTIFICATE_FIELD_TO_SORT_BY_INCORRECT);
        }
    }

    @Override
    public List<ErrorMessage> getMessages() {
        return errors;
    }
}
