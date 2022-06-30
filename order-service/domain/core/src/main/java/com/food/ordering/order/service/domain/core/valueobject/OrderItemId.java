package com.food.ordering.order.service.domain.core.valueobject;

import com.food.ordering.app.common.domain.valueobject.BaseId;

public class OrderItemId extends BaseId<Long> {

    public OrderItemId(Long value) {
        super(value);
    }
}
