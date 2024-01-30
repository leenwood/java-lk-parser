package com.parser.lk.queue;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@Data
@Component
public class OrderMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("currentStatus")
    private String currentStatus;

    @JsonProperty("prevStatus")
    private String prevStatus;

    @JsonProperty("nextStatus")
    private String nextStatus;

    @JsonProperty("orderId")
    private Long orderId;

    public OrderMessage() {
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
                "currentStatus='" + this.currentStatus + '\'' +
                ", prevStatus='" + this.prevStatus + '\'' +
                ", nextStatus='" + this.nextStatus + '\'' +
                ", orderId='" + this.orderId + '\'' +
                '}';
    }
}
