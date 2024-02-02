package com.parser.lk.services.requester.headhunteradapter.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class VacanciesResponse {

    private int pages;

    private int page;

    @JsonProperty("per_page")
    private int perPage;

    private int found;

    private List<VacancyResponse> items;
}
