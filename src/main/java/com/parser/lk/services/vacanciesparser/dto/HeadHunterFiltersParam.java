package com.parser.lk.services.vacanciesparser.dto;


import lombok.Data;

import java.util.List;

/**
 * Данный фильтр предназначен для филтрации в АПИ headhunter все параметры
 * должны совпадать с headhunter api
 */
@Data
public class HeadHunterFiltersParam {

    private int page = 0;

    private int perPage = 100;

    private String text;

    private Boolean onlyWithSalary;

    private Integer period;

    private List<Integer> area;

    private String experience;

    private boolean noMagic = true;

    private List<String> industries;

    /** График */
    private String schedule;

    /** Тип занятонсти */
    private String employment;

    private List<String> vacancySearchFields;


    public HeadHunterFiltersParam(
            String text,
            Boolean onlyWithSalary,
            Integer period,
            List<Integer> area,
            String experience,
            String schedule,
            String employment,
            List<String> vacancySearchFields,
            List<String> industries
    ) {
        this.text = text;
        this.onlyWithSalary = onlyWithSalary;
        this.period = period;
        this.area = area;
        this.experience = experience;
        this.schedule = schedule;
        this.employment = employment;
        this.vacancySearchFields = vacancySearchFields;
        this.industries = industries;
    }
}
