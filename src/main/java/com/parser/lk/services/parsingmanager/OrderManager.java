package com.parser.lk.services.parsingmanager;


import com.parser.lk.dto.request.analytics.OrderGuids;
import com.parser.lk.dto.response.analytics.GetOrdersByGuidResponse;
import com.parser.lk.dto.response.analytics.OrderResponse;
import com.parser.lk.entity.Order;
import com.parser.lk.entity.OrderExcelFileParam;
import com.parser.lk.repository.OrderExcelFileParamRepository;
import com.parser.lk.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderManager {

    @Value("${application.fileoutput.path}")
    private String excelFolderPath;


    private final OrderRepository orderRepository;

    private final OrderExcelFileParamRepository orderExcelFileParamRepository;

    public OrderManager(OrderRepository orderRepository, OrderExcelFileParamRepository orderExcelFileParamRepository) {
        this.orderRepository = orderRepository;
        this.orderExcelFileParamRepository = orderExcelFileParamRepository;
    }


    public GetOrdersByGuidResponse getOrdersByGuid(OrderGuids guids) {
        GetOrdersByGuidResponse orders = new GetOrdersByGuidResponse();
        for (String guid : guids.getGuids()) {

            Optional<Order> orderOptional = this.orderRepository.findOneByGuid(guid);
            if (orderOptional.isEmpty()) {
                continue;
            }


            Order order = orderOptional.get();
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setId(order.getId());
            orderResponse.setGuid(order.getGuid());
            orderResponse.setStatus(order.getStatus());

            Optional<OrderExcelFileParam> optionalOrderExcelFileParam = this.orderExcelFileParamRepository.findOneByGuid(guid);
            if (!optionalOrderExcelFileParam.isEmpty()) {
                OrderExcelFileParam orderExcelFileParam = optionalOrderExcelFileParam.get();
                orderResponse.setFilename(orderExcelFileParam.getFilename());
                orderResponse.setFilePath(
                        String.format(
                                "%s/%s",
                                this.excelFolderPath,
                                orderExcelFileParam.getFilename()
                                )
                );
            }


            orders.getData().add(orderResponse);
        }

        return orders;
    }

}
