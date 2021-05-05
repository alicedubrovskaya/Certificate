package com.epam.esm.service.parser;

import com.epam.esm.model.enumeration.SortOrder;

import java.util.*;

public class SearchParamsParser {
    public List<String> parseFieldsToSortBy(String param) {
        return Arrays.asList(param.split(","));
    }

    public List<String> parseOrdersWithFieldsToFields(String param) {
        List<String> fields = new ArrayList<>();
        String[] params = param.split(",");
        for (String sortWithField : params) {
            fields.add(sortWithField.substring(1));
        }
        return fields;
    }

    public Map<String, SortOrder> parseSortOrders(String param) {
        Map<String, SortOrder> sortOrders = new HashMap<>();
        String[] params = param.split(",");
        Arrays.stream(params).forEach(sortOrder -> {
            if (sortOrder.charAt(0) == '-') {
                sortOrders.put(sortOrder.substring(1), SortOrder.DESC);
            } else {
                sortOrders.put(sortOrder.substring(1), SortOrder.ASC);
            }
        });
        return sortOrders;
    }
}
