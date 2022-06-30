package com.food.ordering.order.service.domain.core.entity;

import com.food.ordering.app.common.domain.entity.BaseEntity;
import com.food.ordering.app.common.domain.valueobject.Money;
import com.food.ordering.app.common.domain.valueobject.OrderId;
import com.food.ordering.order.service.domain.core.valueobject.OrderItemId;
import lombok.Builder;

@Builder
public class OrderItem extends BaseEntity<OrderItemId> {
    private OrderId orderId;
    private Product product;
    private final int quantity;

    private final Money price;

    private final Money subTotal;

    void initialize(OrderId orderId, OrderItemId orderItemId) {
        this.orderId = orderId;
        super.setId(orderItemId);
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getPrice() {
        return price;
    }

    public Money getSubTotal() {
        return subTotal;
    }
}