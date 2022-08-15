package com.food.ordering.app.common.domain.event;

public interface DomainEventPublisher <T extends DomainEvent> {
    void publish(T event);
}
