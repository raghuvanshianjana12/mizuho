package com.order;

import lombok.Data;

import java.util.Objects;

@Data
public class OrderKey implements Comparable<OrderKey> {

    private Long orderId;
    private Long orderPlacedTime;

    public OrderKey(Long orderId) {
        this.orderId = orderId;
        this.orderPlacedTime = System.nanoTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderKey orderKey = (OrderKey) o;
        return orderId.equals(orderKey.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public int compareTo(OrderKey o) {
        return o.getOrderPlacedTime().compareTo(this.getOrderPlacedTime());
    }
}
