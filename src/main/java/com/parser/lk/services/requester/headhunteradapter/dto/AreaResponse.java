package com.parser.lk.services.requester.headhunteradapter.dto;

import lombok.Data;

import java.util.List;

@Data
public class AreaResponse {

    private String id;

    private String name;

    private List<AreaResponse> areas;

}
