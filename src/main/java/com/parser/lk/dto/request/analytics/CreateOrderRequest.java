package com.parser.lk.dto.request.analytics;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    private String searchText;

    private int timestamp;

    private List<String> regionId;

    private boolean allRegion;

    private Boolean hasVmi;

    private Boolean hasSalary;

    private String experience;

    private String externalId;

}
