package com.parser.lk.services.vacanciesparser.dto.vacancies;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Vacancy {
    private String id;

    private String name;

    private String originUrl;

    private Area area;

    private Salary salary;

    private Employment employment;
}
