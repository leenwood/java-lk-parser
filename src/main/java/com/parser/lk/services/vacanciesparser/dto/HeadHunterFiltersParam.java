package com.parser.lk.services.vacanciesparser.dto;


import lombok.Data;

/**
 * Данный фильтр предназначен для филтрации в АПИ headhunter все параметры
 * должны совпадать с headhunter api
 */
@Data
public class HeadHunterFiltersParam implements FilterParamInterface {
    private int per_page;

    @Override
    public String getFilterName() {
        return "HeadHunterFilter";
    }
}
