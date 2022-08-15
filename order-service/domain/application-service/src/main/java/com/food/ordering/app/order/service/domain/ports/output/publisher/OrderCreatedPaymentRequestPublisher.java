package com.food.ordering.app.order.service.domain.ports.output.publisher;

import com.food.ordering.app.common.domain.event.DomainEvent;
import com.food.ordering.order.service.domain.core.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestPublisher extends DomainEvent<OrderCreatedEvent> {
}
