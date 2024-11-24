package com.order.order.comman;

import lombok.Getter;

@Getter

public class ErrorOrderResponse implements OrderResponse {
    private final String EroorMessaage;
    public ErrorOrderResponse(String EroorMessaage) {
        this.EroorMessaage = EroorMessaage;
    }
}
