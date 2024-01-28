package com.example.demo.services.requester.headHunterAdapter.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SalaryResponse {

    @JsonProperty("from")
    private int salaryFrom;

    @JsonProperty("to")
    private int salaryTo;

    private String currency;

    @JsonProperty("salaryGross")
    private boolean salaryGross;

}
