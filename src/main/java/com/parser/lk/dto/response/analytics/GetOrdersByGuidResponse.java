package com.parser.lk.dto.response.analytics;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetOrdersByGuidResponse {

    private List<OrderResponse> data = new ArrayList<>();

}
