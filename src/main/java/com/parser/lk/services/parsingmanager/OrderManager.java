package com.parser.lk.services.parsingmanager;


import com.parser.lk.dto.request.analytics.OrderGuids;
import com.parser.lk.dto.response.analytics.GetOrdersByGuidResponse;
import com.parser.lk.dto.response.analytics.OrderResponse;
import com.parser.lk.entity.Order;
import com.parser.lk.entity.OrderExcelFileParam;
import com.parser.lk.repository.OrderExcelFileParamRepository;
import com.parser.lk.repository.OrderRepository;
import com.parser.lk.repository.VacancyRepository;
import com.parser.lk.services.applicationservice.NameStatusServiceEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderManager {

    @Value("${application.fileoutput.path}")
    private String excelFolderPath;

    @Value("${application.fileoutput.path}")
    private String filesDirectoryPath;

    @Value("${application.hostname}")
    private String serverAddress;

    @Value("${application.http.type}")
    private String httpType;


    @Value("${application.port}")
    private String serverPort;


    private final OrderRepository orderRepository;

    private final OrderExcelFileParamRepository orderExcelFileParamRepository;

    private final VacancyRepository vacancyRepository;

    public OrderManager(
            OrderRepository orderRepository,
            OrderExcelFileParamRepository orderExcelFileParamRepository,
            VacancyRepository vacancyRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderExcelFileParamRepository = orderExcelFileParamRepository;
        this.vacancyRepository = vacancyRepository;
    }

    public GetOrdersByGuidResponse getOrders() {
        GetOrdersByGuidResponse orders = new GetOrdersByGuidResponse();
        for (Order order : this.orderRepository.findAll()) {

            OrderResponse orderResponse = this.getOrderResponse(order);
            if (this.nameStatusServiceHigherParsing(orderResponse.getStatus())) {
                orderResponse.setVacancyCount(this.vacancyRepository.countByGuid(orderResponse.getGuid()));
            }


            orders.getData().add(orderResponse);
        }



        return orders;
    }

    private boolean nameStatusServiceHigherParsing(String status) {
        if (status != NameStatusServiceEnum.PARSING &&
                status != NameStatusServiceEnum.PARSING_ERROR &&
                status != NameStatusServiceEnum.START_PARSING) {
            return true;
        }

        return false;
    }


    public GetOrdersByGuidResponse getOrdersByGuid(OrderGuids guids) {
        GetOrdersByGuidResponse orders = new GetOrdersByGuidResponse();
        for (String guid : guids.getGuids()) {

            Optional<Order> orderOptional = this.orderRepository.findOneByGuid(guid);
            if (orderOptional.isEmpty()) {
                continue;
            }


            Order order = orderOptional.get();
            OrderResponse orderResponse = this.getOrderResponse(order);
            if (this.nameStatusServiceHigherParsing(orderResponse.getStatus())) {
                orderResponse.setVacancyCount(this.vacancyRepository.countByGuid(orderResponse.getGuid()));
            }


            orders.getData().add(orderResponse);
        }

        return orders;
    }


    private OrderResponse getOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setGuid(order.getGuid());
        orderResponse.setStatus(order.getStatus());

        Optional<OrderExcelFileParam> optionalOrderExcelFileParam = this.orderExcelFileParamRepository.findOneByGuid(order.getGuid());
        if (optionalOrderExcelFileParam.isPresent()) {
            OrderExcelFileParam orderExcelFileParam = optionalOrderExcelFileParam.get();
            orderResponse.setFilename(orderExcelFileParam.getFilename());
            orderResponse.setFilePath(this.createDownloadUrl(orderExcelFileParam.getGuid()));
        }
        return orderResponse;
    }


    public String createDownloadUrl(String guid) {
        return String.format(
                "%s://%s:%s/api/v1/files/excel/%s",
                this.httpType,
                this.serverAddress,
                this.serverPort,
                guid
        );
    }

}
