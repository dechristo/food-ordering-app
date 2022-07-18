package com.food.ordering.order.service.domain.core.event;

import com.food.ordering.order.service.domain.core.entity.Order;

import java.time.ZonedDateTime;

public class OrderCancelledEvent extends OrderEvent {

    public OrderCancelledEvent(Order order, ZonedDateTime createdAt) {
       super(order, createdAt);
    }
}
