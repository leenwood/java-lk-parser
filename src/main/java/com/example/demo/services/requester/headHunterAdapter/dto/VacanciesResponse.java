package com.example.demo.services.requester.headHunterAdapter.dto;


import lombok.Data;

import java.util.List;

@Data
public class VacanciesResponse {

    private int pages;

    private int page;

    private int per_page;

    private int found;

    private List<VacancyResponse> items;
}
