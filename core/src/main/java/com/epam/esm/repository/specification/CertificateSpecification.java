package com.epam.esm.repository.specification;

import com.epam.esm.model.enumeration.SortOrder;
import com.epam.esm.service.dto.SearchCertificateDto;
import com.epam.esm.service.parser.SearchParamsParser;

import java.util.List;
import java.util.Map;

public class CertificateSpecification {
    private final SearchParamsParser parser = new SearchParamsParser();

    private final String tagName;
    private final String certificateName;
    private final String certificateDescription;
    private final List<String> fieldsToSortBy;
    private final Map<String, SortOrder> sortOrders;

    private static final String LIKE = "LIKE '%";
    private static final String WHERE = "WHERE ";
    private static final String AND = "AND ";
    private static final String ORDER_BY = "ORDER BY ";
    private static final String SELECT = "SELECT * FROM ";
    private static final String TAG_NAME_FILTER = "id IN (SELECT gift_certificate_id FROM tag_gift_certificate JOIN tag " +
            "ON tag.id=tag_gift_certificate.tag_id WHERE tag.name=";

    public CertificateSpecification(SearchCertificateDto searchCertificateDto) {
        this.tagName = searchCertificateDto.getTagName();
        this.certificateName = searchCertificateDto.getCertificateName();
        this.certificateDescription = searchCertificateDto.getDescription();
        this.fieldsToSortBy = parser.parseFieldsToSortBy(searchCertificateDto.getFieldsToSortBy());
        this.sortOrders = parser.parseSortOrders(searchCertificateDto.getSortOrders());
    }

    public String buildSql() {
        StringBuilder sql = new StringBuilder(SELECT + "gift_certificate ");
        addWherePart(sql);
        addSortPart(sql);
        return sql.toString();
    }

    private void addWherePart(StringBuilder sql) {
        sql.append(WHERE + "TRUE ");
        if (tagName != null) {
            sql.append(AND + TAG_NAME_FILTER + "'")
                    .append(tagName)
                    .append("') ");
        }
        if (certificateName != null) {
            sql.append(AND + "name " + LIKE)
                    .append(certificateName)
                    .append("%' ");
        }
        if (certificateDescription != null) {
            sql.append(AND + "description " + LIKE)
                    .append(certificateDescription)
                    .append("%' ");
        }
    }

    private void addSortPart(StringBuilder sql) {
        if (fieldsToSortBy != null && !fieldsToSortBy.isEmpty()) {
            sql.append(ORDER_BY);
            SortOrder sortOrder;
            String fieldToSortBy;
            for (int i = 0; i < fieldsToSortBy.size(); i++) {
                fieldToSortBy = fieldsToSortBy.get(i);
                sortOrder = sortOrders.getOrDefault(fieldToSortBy, SortOrder.ASC);
                sql.append(fieldToSortBy)
                        .append(" ")
                        .append(sortOrder);
                if (i < fieldsToSortBy.size() - 1) {
                    sql.append(", ");
                }
            }
        }
    }
}
