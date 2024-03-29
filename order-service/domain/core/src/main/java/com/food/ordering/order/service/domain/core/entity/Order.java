package com.food.ordering.order.service.domain.core.entity;

import com.food.ordering.app.common.domain.entity.AggregateRoot;
import com.food.ordering.app.common.domain.valueobject.*;
import com.food.ordering.order.service.domain.core.exception.OrderDomainException;
import com.food.ordering.order.service.domain.core.valueobject.OrderItemId;
import com.food.ordering.order.service.domain.core.valueobject.TrackingId;
import lombok.Builder;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Builder
public class Order extends AggregateRoot<OrderId> {

    private final RestaurantId restaurantId;
    private final CustomerId customerId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> items;

    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    public void initialize() {
        setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initializeOrderItems();
    }

    public void validateOrder() {
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    public void pay() {
        if (orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException(" Order is not in correct state for pay operation.");
        }

        orderStatus = OrderStatus.PAID;
    }


    public void approve() {
        if (orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException(" Order is not in correct state for approve operation.");
        }

        orderStatus = OrderStatus.APPROVED;
    }

    public void initCancel(List<String> failureMessages) {
        if (orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException(" Order is not in correct state for initCancel operation.");
        }

        orderStatus = OrderStatus.CANCELLING;
        updateFailureMessages(failureMessages);
    }

    public void cancel(List<String> failureMessages) {
        if (!(orderStatus == OrderStatus.CANCELLING || orderStatus == OrderStatus.PENDING)) {
            throw new OrderDomainException(" Order is not in correct state for canceö operation.");
        }

        orderStatus = OrderStatus.CANCELLED;
        updateFailureMessages(failureMessages);
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if (this.failureMessages != null && failureMessages != null) {
            this.failureMessages.addAll(failureMessages.stream().filter(message -> !message.isEmpty()).toList());
        }

        if (this.failureMessages == null) {
            this.failureMessages = failureMessages;
        }
    }

    private void validateInitialOrder() {
        if (orderStatus != null || getId() != null) {
            throw new OrderDomainException("Order is not in the correct state for initialization.");
        }
    }

    private void validateTotalPrice() {
        if (price == null || !price.isGreaterThanZero()) {
            throw new OrderDomainException(" Total price must be greater than zero.");
        }
    }

    private void validateItemsPrice() {
        Money orderItemsTotal = items.stream()
            .map( orderItem -> {
                validateItemPrice(orderItem);
                return orderItem.getSubTotal();
            })
            .reduce(Money.ZERO, Money::add);

        if (!price.equals(orderItemsTotal)) {
            throw new OrderDomainException("Total price: [" + price.getAmount() + "]" +
                "is not equal to order items total: [" + orderItemsTotal.getAmount() + "]");
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (!orderItem.isPriceValid()) {
            throw new OrderDomainException("Order item price: " + orderItem.getPrice().getAmount() +
                " is not valid for product " + orderItem.getProduct().getName());
        }
    }

    private void initializeOrderItems() {
        AtomicLong itemId = new AtomicLong(1);
        items.forEach(orderItem -> orderItem.initialize(super.getId(), new OrderItemId(itemId.getAndIncrement())));
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public StreetAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }
}

