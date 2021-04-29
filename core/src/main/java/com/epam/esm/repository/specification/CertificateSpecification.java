package com.epam.esm.repository.specification;

import com.epam.esm.service.dto.SearchCertificateDto;

import java.util.List;

public class CertificateSpecification {

    private final String tagName;
    private final String certificateName;
    private final String certificateDescription;
    private final List<String> fieldsToSortBy;
    private final String sortOrder;

    private static final String LIKE = "LIKE '%";
    private static final String WHERE = "WHERE ";
    private static final String AND = "AND ";
    private static final String DESC = "DESC";
    private static final String ORDER_BY = "ORDER BY ";
    private static final String TAG_NAME_FILTER = "id IN (SELECT gift_certificate_id FROM tag_gift_certificate JOIN tag " +
            "ON tag.id=tag_gift_certificate.tag_id WHERE tag.name=";

    public CertificateSpecification(SearchCertificateDto searchCertificateDto) {
        this.tagName = searchCertificateDto.getTagName();
        this.certificateName = searchCertificateDto.getCertificateName();
        this.certificateDescription = searchCertificateDto.getDescription();
        this.fieldsToSortBy = searchCertificateDto.getFieldsToSortBy();
        this.sortOrder = searchCertificateDto.getSortOrder();
    }

    private StringBuilder getWherePart() {
        StringBuilder whereQuery = new StringBuilder();
        if (tagName != null || certificateName != null || certificateDescription != null) {
            whereQuery.append(WHERE + "TRUE ");
            if (tagName != null) {
                whereQuery.append(AND + TAG_NAME_FILTER)
                        .append(tagName)
                        .append(") ");
            }
            if (certificateName != null) {
                whereQuery.append(AND + "name " + LIKE)
                        .append(certificateName)
                        .append("%' ");
            }
            if (certificateDescription != null) {
                whereQuery.append(AND + "description " + LIKE)
                        .append(certificateDescription)
                        .append("%' ");
            }
        }
        return whereQuery;
    }

    private StringBuilder getSortPart() {
        StringBuilder sortQuery = new StringBuilder();
        if (!fieldsToSortBy.isEmpty()) {
            sortQuery.append(ORDER_BY);
            for (int i = 0; i < fieldsToSortBy.size(); i++) {
                sortQuery.append(fieldsToSortBy.get(i)).append(" ");
                if (DESC.equals(sortOrder)) {
                    sortQuery.append(sortOrder);
                }
                if (i < fieldsToSortBy.size() - 1) {
                    sortQuery.append(", ");
                }
            }
        }
        return sortQuery;
    }

    private String queryBuilder() {
        String startQuery = "SELECT * FROM gift_certificate ";
        StringBuilder finalQuery = new StringBuilder(startQuery);

        StringBuilder whereQuery = getWherePart();
        if (!whereQuery.toString().isEmpty()) {
            finalQuery.append(whereQuery);
        }

        StringBuilder sortQuery = getSortPart();
        if (!sortQuery.toString().isEmpty()) {
            finalQuery.append(sortQuery);
        }
        return finalQuery.toString();
    }

    public String toSql() {
        return queryBuilder();
    }
}
