package com.food.ordering.app.order.service.domain.core.entity;

import com.food.ordering.app.common.domain.entity.AggregateRoot;
import com.food.ordering.app.common.domain.valueobject.*;

public class Order extends AggregateRoot<OrderId> {

    private final RestaurantId restaurantId;
    private final CustomerId customerId;
    private final StreetAddress deliveryAddress;
    private final Money price;
}
