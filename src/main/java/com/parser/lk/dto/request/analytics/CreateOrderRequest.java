package com.parser.lk.dto.request.analytics;

import lombok.Data;

@Data
public class CreateOrderRequest {

    private String searchText;

    private int timestamp;

    private String regionId;

    private boolean allRegion;

    private boolean hasVmi;

    private boolean hasSalary;

    private String externalId;

}
