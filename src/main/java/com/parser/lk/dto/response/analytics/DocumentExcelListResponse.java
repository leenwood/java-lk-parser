package com.parser.lk.dto.response.analytics;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DocumentExcelListResponse {

    private Integer count;

    private List<DocumentExcelResponse> data = new ArrayList<>();


}
