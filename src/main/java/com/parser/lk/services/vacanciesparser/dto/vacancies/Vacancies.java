package com.parser.lk.services.vacanciesparser.dto.vacancies;

import lombok.Data;

import java.util.List;

@Data
public class Vacancies {

    private int pages;

    private int page;

    private int per_page;

    private int found;

    private List<Vacancy> vacancies;

}
