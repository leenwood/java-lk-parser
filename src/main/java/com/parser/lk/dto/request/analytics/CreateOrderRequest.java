package com.parser.lk.dto.request.analytics;

import lombok.Data;

@Data
public class CreateOrderRequest {

    private String searchText;

    private int timestamp;

    private String[] regionId;

    private boolean allRegion;

    private Boolean hasVmi;

    private Boolean hasSalary;

    private String experience;

    private String externalId;

}
