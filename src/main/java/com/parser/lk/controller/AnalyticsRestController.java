package com.parser.lk.controller;


import com.parser.lk.dto.request.analytics.CreateOrderRequest;
import com.parser.lk.dto.request.analytics.OrderGuids;
import com.parser.lk.dto.response.analytics.GetOrdersByGuidResponse;
import com.parser.lk.dto.response.analytics.SuccessResponse;
import com.parser.lk.services.parsingmanager.OrderManager;
import com.parser.lk.services.parsingmanager.ParserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsRestController {


    private final ParserManager parserManager;

    private final OrderManager orderManager;

    @Autowired
    public AnalyticsRestController(ParserManager parserManager, OrderManager orderManager) {
        this.parserManager = parserManager;
        this.orderManager = orderManager;
    }


    @GetMapping("/work-test")
    public SuccessResponse serverResponse() {
        SuccessResponse response = new SuccessResponse();
        response.setStatus(true);
        return response;
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

}
