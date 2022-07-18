package com.food.ordering.order.service.domain.core.entity;

import com.food.ordering.app.common.domain.entity.AggregateRoot;
import com.food.ordering.app.common.domain.valueobject.RestaurantId;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Restaurant extends AggregateRoot<RestaurantId> {

    private final List<Product> products;
    private boolean active;

    public Restaurant(RestaurantId restaurantId, List<Product> products, Boolean active) {
        super.setId(restaurantId);
        this.products = products;
        this.active = active;
    }
}

