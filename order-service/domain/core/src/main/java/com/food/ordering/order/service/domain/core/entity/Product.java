package com.food.ordering.order.service.domain.core.entity;

import com.food.ordering.app.common.domain.entity.BaseEntity;
import com.food.ordering.app.common.domain.valueobject.Money;
import com.food.ordering.app.common.domain.valueobject.ProductId;
import lombok.Data;

@Data
public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productId, String name, Money price) {
        super.setId(productId);
        this.name = name;
        this.price = price;
    }
}
