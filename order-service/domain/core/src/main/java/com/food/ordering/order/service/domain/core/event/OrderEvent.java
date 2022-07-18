package com.food.ordering.order.service.domain.core.event;

import com.food.ordering.app.common.domain.event.DomainEvent;
import com.food.ordering.order.service.domain.core.entity.Order;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public abstract class OrderEvent implements DomainEvent<Order> {
    private final Order order;
    private final ZonedDateTime createdAt;

    public OrderEvent(Order order, ZonedDateTime createdAt) {
        this.order = order;
        this.createdAt = createdAt;
    }
}
