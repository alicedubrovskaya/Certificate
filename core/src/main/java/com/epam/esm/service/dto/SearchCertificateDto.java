package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCertificateDto {
    //TODO validator
    private String tagName;
    private String certificateName;
    private String description;
    private List<String> fieldsToSortBy;
    private String sortOrder;
}