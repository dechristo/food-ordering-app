package com.food.ordering.app.order.service.domain.mapper;

import com.food.ordering.app.common.domain.valueobject.*;
import com.food.ordering.app.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.app.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.app.order.service.domain.dto.create.OrderAddress;
import com.food.ordering.order.service.domain.core.entity.Order;
import com.food.ordering.order.service.domain.core.entity.OrderItem;
import com.food.ordering.order.service.domain.core.entity.Product;
import com.food.ordering.order.service.domain.core.entity.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {
    public Restaurant buildRestaurantFromCreateOrderCommand(CreateOrderCommand createOrderCommand) {
        return Restaurant.builder()
            .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
            .products(createOrderCommand.getItems().stream().map( orderItem ->
                new Product(new ProductId(orderItem.getProductId())))
                .collect(Collectors.toList())
            )
            .build();
    }

    public Order buildOrderFromCreateOrderCommand(CreateOrderCommand createOrderCommand) {
        return Order.builder()
            .customerId( new CustomerId(createOrderCommand.getCustomerId()))
            .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
            .deliveryAddress(buildStreetAddressFromOrderAddress(createOrderCommand.getAddress()))
            .price(new Money(createOrderCommand.getPrice()))
            .items(buildOrderItemEntitiesFromOrderItems(createOrderCommand.getItems()))
            .build();
    }


    public CreateOrderResponse buildOrderResponseFromOrder(Order order, String message) {
        return CreateOrderResponse
            .builder()
            .orderTrackingId(order.getTrackingId().getValue())
            .orderStatus(order.getOrderStatus())
            .message(message)
            .build();
    }

    private List<OrderItem> buildOrderItemEntitiesFromOrderItems(
        List<com.food.ordering.app.order.service.domain.dto.create.OrderItem> orderItems) {
        return orderItems
            .stream()
            .map(orderItem ->
                OrderItem.builder()
                    .product(new Product(new ProductId(orderItem.getProductId())))
                    .price(new Money(orderItem.getPrice()))
                    .quantity(orderItem.getQuantity())
                    .subTotal(new Money(orderItem.getSubTotal()))
                    .build())
            .collect(Collectors.toList());
    }

    private StreetAddress buildStreetAddressFromOrderAddress(OrderAddress orderAddress) {
        return new StreetAddress(
            UUID.randomUUID(),
            orderAddress.getStreet(),
            orderAddress.getPostalCode(),
            orderAddress.getCity()
        );
    }
}
