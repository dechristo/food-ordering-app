package com.food.ordering.app.order.service.domain.ports.output.repository;

import com.food.ordering.order.service.domain.core.entity.Order;
import com.food.ordering.order.service.domain.core.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findByTrackingId(TrackingId trackingId);
}
