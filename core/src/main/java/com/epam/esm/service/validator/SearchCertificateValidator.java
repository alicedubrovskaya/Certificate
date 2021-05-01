package com.epam.esm.service.validator;

import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.model.enumeration.SortOrder;
import com.epam.esm.service.dto.SearchCertificateDto;
import org.apache.commons.lang3.EnumUtils;
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
        if (validateDto(searchCertificateDto)) {
            validateFieldsToSortBy(searchCertificateDto);
//            validateSortOrder(searchCertificateDto);
        }
    }

    protected boolean validateDto(SearchCertificateDto searchCertificateDto) {
        if (searchCertificateDto == null) {
            errors.add(ErrorMessage.SEARCH_CERTIFICATE_DTO_EMPTY);
            return false;
        }
        return true;
    }

    protected void validateFieldsToSortBy(SearchCertificateDto searchCertificateDto) {
        List<String> fieldToSortBy = searchCertificateDto.getFieldsToSortBy();
        if (fieldToSortBy != null && fieldToSortBy.stream().anyMatch(field -> !CORRECT_FIELDS_TO_SORT_BY.contains(field))) {
            errors.add(ErrorMessage.SEARCH_CERTIFICATE_FIELD_TO_SORT_BY_INCORRECT);
        }
    }

//    protected void validateSortOrder(SearchCertificateDto searchCertificateDto) {
//        String sortOrder = searchCertificateDto.getSortOrder();
//        if (sortOrder != null && !EnumUtils.isValidEnum(SortOrder.class, sortOrder)) {
//            errors.add(ErrorMessage.CERTIFICATE_SORT_ORDER_INCORRECT);
//        }
//    }

    @Override
    public List<ErrorMessage> getMessages() {
        return errors;
    }
}
