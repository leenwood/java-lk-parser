package com.parser.lk.services.requester.headhunteradapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VacancyResponse {

    private String id;

    private String name;

    @JsonProperty("alternate_url")
    private String originUrl;

    private AreaResponse area;

    private SalaryResponse salary;

    private EmploymentResponse employment;

    private ExperienceResponse experience;

    private ScheduleResponse schedule;

    private String Description;
}
