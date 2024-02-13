package com.parser.lk.dto.request.analytics;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    private String searchText;

    private int timestamp;

    private List<Integer> regionId;

    private boolean allRegion;

    private Boolean hasVmi;

    private Boolean hasSalary;

    private String experience;

    private List<String> industries;

    /** График */
    private String schedule;

    /** Тип занятонсти */
    private String employment;

    private List<String> vacancySearchFields;

    private String externalId;

}
