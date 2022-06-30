package com.food.ordering.order.service.domain.core.entity;

import com.food.ordering.app.common.domain.entity.AggregateRoot;
import com.food.ordering.app.common.domain.valueobject.*;
import com.food.ordering.order.service.domain.core.valueobject.OrderItemId;
import com.food.ordering.order.service.domain.core.valueobject.TrackingId;
import lombok.Builder;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Builder
public class Order extends AggregateRoot<OrderId> {

    private final RestaurantId restaurantId;
    private final CustomerId customerId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> items;

    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    public void initialize() {
        setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initializeOrderItems();
    }

    private void initializeOrderItems() {
        AtomicLong itemId = new AtomicLong(1);
        items.forEach(orderItem -> orderItem.initialize(super.getId(), new OrderItemId(itemId.getAndIncrement())));
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public StreetAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }
}

