package com.order.order.comman;

import com.order.order.dto.OrderDTO;
import lombok.Getter;

@Getter
public class SuccessOrderResponse implements OrderResponse {
private final OrderDTO order;

public SuccessOrderResponse(OrderDTO order) {
    this.order = order;
}

}