package com.parser.lk.services.requester.chatgpt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatGPTResponse {

    private Boolean VMI;

    private String functional;

    private List<String> keySkills;

    private String grade;

}
