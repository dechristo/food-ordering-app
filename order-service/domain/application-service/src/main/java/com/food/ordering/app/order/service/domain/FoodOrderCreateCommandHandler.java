package com.food.ordering.app.order.service.domain;

import com.food.ordering.app.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.app.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.app.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.app.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.app.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.app.order.service.domain.ports.output.repository.RestaurantRepository;
import com.food.ordering.order.service.domain.core.OrderDomainService;
import com.food.ordering.order.service.domain.core.entity.Customer;
import com.food.ordering.order.service.domain.core.entity.Order;
import com.food.ordering.order.service.domain.core.entity.Restaurant;
import com.food.ordering.order.service.domain.core.event.OrderCreatedEvent;
import com.food.ordering.order.service.domain.core.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class FoodOrderCreateCommandHandler {

    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final CustomerRepository customerRepository;
    private final OrderDataMapper orderDataMapper;

    public FoodOrderCreateCommandHandler(OrderDomainService orderDomainService,
                                         OrderRepository orderRepository,
                                         RestaurantRepository restaurantRepository,
                                         CustomerRepository customerRepository,
                                         OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.customerRepository = customerRepository;
        this.orderDataMapper = orderDataMapper;
    }

    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand){
        checkCustomer(createOrderCommand.getCustomerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.buildOrderFromCreateOrderCommand(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.initializeOrder(order, restaurant);
        Order createdOrder = saveOrder(order);
        log.info("New order created: %s".formatted(createdOrder.getId()));
        return orderDataMapper
            .buildOrderResponseFromOrder(createdOrder, "New order created: %s".formatted(createdOrder.getId()));
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);

        if(customer.isEmpty()) {
            log.warn("Could not find customer with id: %s".formatted(customerId));
            throw new OrderDomainException("Could not find customer with id: %s".formatted(customerId));
        }
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderDataMapper.buildRestaurantFromCreateOrderCommand(createOrderCommand);
        Optional<Restaurant> restaurantOptional = restaurantRepository.findRestaurantInformation(restaurant);

        if (restaurantOptional.isEmpty()) {
            log.warn("Could not find restaurant with id %s".formatted(restaurantOptional.get().getRestaurantId()));
            throw new OrderDomainException("Could not find restaurant with id %s"
                .formatted(restaurantOptional.get().getRestaurantId()));
        }
        return restaurantOptional.get();
    }

    private Order saveOrder(Order order) {
        Order savedOrder = orderRepository.save(order);

        if (savedOrder == null) {
            log.error("Could not save order.");
            throw new OrderDomainException("Could not save order.");
        }
        log.info("Order saved successfully.");
        return order;
    }
}
