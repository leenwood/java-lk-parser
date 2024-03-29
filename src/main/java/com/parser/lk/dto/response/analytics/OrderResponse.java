package com.parser.lk.dto.response.analytics;


import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {

    private String guid;

    private Long id;

    private Integer vacancyCount;

    private String status;

    private String filePath;

    private String filename;

}
