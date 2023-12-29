package com.magicpostapi.dto;

import com.magicpostapi.models.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderDto {
    private String orderId;
    private String startLocation;
    private String endLocation;
    private String type;
    private String orderStatus;

    public OrderDto(Order order) {
        this.orderId = order.getId();
        this.startLocation = order.getTransactionPointFrom().getName();
        this.endLocation = order.getTransactionPointTo().getName();
        this.type = order.getType();
        this.orderStatus = order.getOrderStatus().toString();

    }
}
