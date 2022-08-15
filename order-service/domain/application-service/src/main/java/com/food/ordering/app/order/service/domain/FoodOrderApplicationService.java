package com.food.ordering.app.order.service.domain;

import com.food.ordering.app.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.app.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.app.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.app.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.app.order.service.domain.ports.input.OrderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Slf4j
class FoodOrderApplicationService implements OrderApplicationService {

    private final FoodOrderCreateCommandHandler foodOrderCreateCommandHandler;
    private final FoodOrderTrackCommandHandler foodOrderTrackCommandHandler;

    public FoodOrderApplicationService(FoodOrderCreateCommandHandler foodOrderCreateCommandHandler,
                                       FoodOrderTrackCommandHandler foodOrderTrackCommandHandler) {
        this.foodOrderCreateCommandHandler = foodOrderCreateCommandHandler;
        this.foodOrderTrackCommandHandler = foodOrderTrackCommandHandler;
    }

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return foodOrderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return foodOrderTrackCommandHandler.trackOrder(trackOrderQuery);
    }
}
