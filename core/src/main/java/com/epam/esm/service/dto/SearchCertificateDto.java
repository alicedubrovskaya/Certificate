package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCertificateDto {
    private String tagName;
    private String certificateName;
    private String description;
    private String fieldsToSortBy;
    private String sortOrders;
}