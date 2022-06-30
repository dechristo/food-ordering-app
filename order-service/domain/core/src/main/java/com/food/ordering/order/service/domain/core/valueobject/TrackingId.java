package com.food.ordering.order.service.domain.core.valueobject;

import com.food.ordering.app.common.domain.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<Long> {

    public TrackingId(UUID value) {
        super(value);
    }
}