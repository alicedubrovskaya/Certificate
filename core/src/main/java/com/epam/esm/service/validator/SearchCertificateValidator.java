package com.epam.esm.service.validator;

import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.service.dto.SearchCertificateDto;
import com.epam.esm.service.parser.SearchParamsParser;
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
    private final SearchParamsParser parser = new SearchParamsParser();
    private final List<ErrorMessage> errors = new ArrayList<>();
    private static final String CORRECT_FIELDS_ENUMERATION = "^([a-z]+[,])*[a-z]+$";
    private static final String CORRECT_SORT_ORDERS_ENUMERATION = "^([\\-|\\s][a-z]+,)*[\\-|\\s][a-z]+$";
    private static final List<String> CORRECT_FIELDS_TO_SORT_BY = Arrays.asList("id", "name", "description",
            "cost", "currency", "duration", "create_date", "last_update_date");

    @Override
    public void validate(SearchCertificateDto searchCertificateDto) {
        validateFieldsToSortBy(searchCertificateDto.getFieldsToSortBy());
        validateSortOrders(searchCertificateDto.getSortOrders());
    }

    protected void validateFieldsToSortBy(String fields) {
        if (fields != null && (!fields.matches(CORRECT_FIELDS_ENUMERATION) ||
                parser.parseFieldsToSortBy(fields).stream().anyMatch(field -> !CORRECT_FIELDS_TO_SORT_BY.contains(field)))) {
            errors.add(ErrorMessage.SEARCH_CERTIFICATE_FIELD_TO_SORT_BY_INCORRECT);
        }
    }

    protected void validateSortOrders(String sortOrders) {
        if (sortOrders != null && (!sortOrders.matches(CORRECT_SORT_ORDERS_ENUMERATION) ||
                parser.parseOrdersWithFieldsToFields(sortOrders).stream().anyMatch(field -> !CORRECT_FIELDS_TO_SORT_BY.contains(field)))) {
            errors.add(ErrorMessage.SEARCH_CERTIFICATE_SORT_ORDERS_INCORRECT);
        }
    }

    @Override
    public List<ErrorMessage> getMessages() {
        return errors;
    }
}
