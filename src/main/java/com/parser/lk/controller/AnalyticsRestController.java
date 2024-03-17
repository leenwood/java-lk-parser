package com.parser.lk.controller;


import com.parser.lk.dto.request.analytics.CreateOrderRequest;
import com.parser.lk.dto.request.analytics.OrderGuids;
import com.parser.lk.dto.response.analytics.GetOrdersByGuidResponse;
import com.parser.lk.dto.response.analytics.MathematicsResult;
import com.parser.lk.dto.response.analytics.SuccessResponse;
import com.parser.lk.services.calculationservice.MathematicsManager;
import com.parser.lk.services.parsingmanager.OrderManager;
import com.parser.lk.services.parsingmanager.ParserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsRestController {


    private final ParserManager parserManager;

    private final OrderManager orderManager;

    private final MathematicsManager mathematicsManager;

    @Autowired
    public AnalyticsRestController(
            ParserManager parserManager,
            OrderManager orderManager,
            MathematicsManager mathematicsManager
    ) {
        this.parserManager = parserManager;
        this.orderManager = orderManager;
        this.mathematicsManager = mathematicsManager;
    }

    @PostMapping("/create/order")
    public CompletableFuture<SuccessResponse> startParsing(@RequestBody CreateOrderRequest createOrderObject) {
        boolean result = this.parserManager.startWork(createOrderObject);

        SuccessResponse response = new SuccessResponse();
        response.setStatus(result);
        return CompletableFuture.completedFuture(response);
    }

    @GetMapping("/orders")
    public GetOrdersByGuidResponse getOrders() {
        return this.orderManager.getOrders();
    }

    @PostMapping("/orders")
    public GetOrdersByGuidResponse getOrdersByGuid(@RequestBody OrderGuids orderGuids) {
        return this.orderManager.getOrdersByGuid(orderGuids);
    }

    @GetMapping("/orders/result")
    public List<MathematicsResult> getMathematicsByResult(
            @RequestParam(name= "guid") String guid
    ) {
        return this.mathematicsManager.getResultByGuid(guid);
    }

}
