package com.food.ordering.app.common.domain.valueobject;

import java.util.UUID;

public class CustomerId extends BaseId<UUID>{

    protected CustomerId(UUID value) {
        super(value);
    }
}
