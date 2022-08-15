package com.food.ordering.app.order.service.domain.ports.input;

import com.food.ordering.app.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.app.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.app.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.app.order.service.domain.dto.track.TrackOrderResponse;

import javax.validation.Valid;

public interface OrderApplicationService {
    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);
    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
