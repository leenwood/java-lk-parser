package com.parser.lk.services.requester.sendrequest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationBodyRequest {

    private String currentStatus;

    private String nextStatus;

    private String prevStatus;

    private boolean success;

    private Long orderId;



}
