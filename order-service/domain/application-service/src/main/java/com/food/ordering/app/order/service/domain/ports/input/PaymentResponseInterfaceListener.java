package com.food.ordering.app.order.service.domain.ports.input;

import com.food.ordering.app.order.service.domain.dto.message.PaymentResponse;

public interface PaymentResponseInterfaceListener {
    void paymentCompleted(PaymentResponse paymentResponse);
    void paymentCancelled(PaymentResponse paymentResponse);
}
