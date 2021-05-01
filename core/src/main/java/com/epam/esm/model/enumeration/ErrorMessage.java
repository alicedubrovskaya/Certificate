package com.epam.esm.model.enumeration;

public enum ErrorMessage {
    RESOURCE_NOT_FOUND("Requested resource not found"),

    EMPTY_PARAMETER("Empty parameter %s"),
    ID_INCORRECT("Incorrect id"),
    TAG_DTO_EMPTY("Tag is null"),
    TAG_NAME_EMPTY("Tag name is null"),
    TAG_NAME_INCORRECT("Parameter 'name' may consist of 2-255 characters that can be latin letters and spaces"),
    CERTIFICATE_DTO_EMPTY("Certificate is null"),
    CERTIFICATE_NAME_EMPTY("Certificate name is null"),
    CERTIFICATE_NAME_INCORRECT("Parameter 'name' of certificate may consist of 5-255 characters that can be latin letters and spaces"),
    CERTIFICATE_DESCRIPTION_INCORRECT("Parameter 'description' of certificate may consist of 10-400 characters"),
    CERTIFICATE_PRICE_COST_INCORRECT("Parameter 'cost' of certificate may contain 1-10 digits in the integral part and 1-4 in the fraction part of the number."),
    CERTIFICATE_DURATION_INCORRECT("Parameter 'duration' of certificate should be more than zero"),
    SEARCH_CERTIFICATE_DTO_EMPTY("SearchCertificateDto is null"),
    SEARCH_CERTIFICATE_FIELD_TO_SORT_BY_INCORRECT("SearchCertificateDto may contain fields: id, name, description," +
            "cost, currency,duration,create_date,last_update_date"),
    CERTIFICATE_SORT_ORDER_INCORRECT("Parameter 'sortOrder' may be ASC or DESC");

    private final String type;

    ErrorMessage(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
