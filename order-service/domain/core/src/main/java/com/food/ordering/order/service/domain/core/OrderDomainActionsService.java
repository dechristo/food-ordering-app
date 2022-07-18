package com.food.ordering.order.service.domain.core;

import com.food.ordering.order.service.domain.core.entity.Order;
import com.food.ordering.order.service.domain.core.entity.Product;
import com.food.ordering.order.service.domain.core.entity.Restaurant;
import com.food.ordering.order.service.domain.core.event.OrderCancelledEvent;
import com.food.ordering.order.service.domain.core.event.OrderCreatedEvent;
import com.food.ordering.order.service.domain.core.event.OrderPaidEvent;
import com.food.ordering.order.service.domain.core.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainActionsService implements OrderDomainService {

    private static String UTC = "UTC";

    @Override
    public OrderCreatedEvent initialiazeOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInformation(order, restaurant);
        order.validateOrder();
        order.initialize();

        log.info("Order {} initialized.", order.getId());
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order {} is paid.", order.getId());
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order {} is approved.", order.getId());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.initCancel(failureMessages);
        log.info("Order {} is being cancelled.", order.getId());
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order {} is cancelled.", order.getId());
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.isActive()) {
            throw new OrderDomainException("Restaurant %s is not active.".formatted(restaurant.getId()));
        }
    }

    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        order.getItems()
            .forEach(orderItem ->
                restaurant.getProducts().forEach(restaurantProduct -> {
                    Product currentProduct = orderItem.getProduct();
                    if (currentProduct.equals(restaurantProduct)) {
                        currentProduct.updateWithConfirmedNameAndPrice(restaurantProduct.getName(), restaurantProduct.getPrice());
                    }
        }));
    }
}
