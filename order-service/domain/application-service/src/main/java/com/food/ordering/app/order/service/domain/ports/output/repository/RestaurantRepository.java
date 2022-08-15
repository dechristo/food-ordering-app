package com.food.ordering.app.order.service.domain.ports.output.repository;

import com.food.ordering.order.service.domain.core.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {
    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
