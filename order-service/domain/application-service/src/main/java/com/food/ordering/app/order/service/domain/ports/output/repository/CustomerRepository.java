package com.food.ordering.app.order.service.domain.ports.output.repository;

import com.food.ordering.order.service.domain.core.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> findCustomer(UUID customerId);
}
