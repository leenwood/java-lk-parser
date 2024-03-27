package com.parser.lk.services.vacanciesparser.dto.vacancies;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Salary {

    @JsonProperty("from")
    private int salaryFrom;

    @JsonProperty("to")
    private int salaryTo;

    private String currency;

    @JsonProperty("gross")
    private boolean salaryGross;

}
