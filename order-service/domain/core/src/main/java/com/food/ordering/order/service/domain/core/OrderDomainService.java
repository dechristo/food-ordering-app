package com.food.ordering.order.service.domain.core;

import com.food.ordering.order.service.domain.core.entity.Order;
import com.food.ordering.order.service.domain.core.entity.Restaurant;
import com.food.ordering.order.service.domain.core.event.OrderCancelledEvent;
import com.food.ordering.order.service.domain.core.event.OrderCreatedEvent;
import com.food.ordering.order.service.domain.core.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent initializeOrder(Order order, Restaurant restaurant);

    OrderPaidEvent payOrder(Order order);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);
}
