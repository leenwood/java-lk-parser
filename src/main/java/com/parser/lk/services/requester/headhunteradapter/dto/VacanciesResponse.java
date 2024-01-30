package com.parser.lk.services.requester.headhunteradapter.dto;


import com.parser.lk.services.requester.VacanciesResponseInterface;
import lombok.Data;

import java.util.List;

@Data
public class VacanciesResponse implements VacanciesResponseInterface {

    private int pages;

    private int page;

    private int per_page;

    private int found;

    private List<VacancyResponse> items;
}
