package com.order;

import lombok.Data;

import java.util.Objects;
@Data
public class Order {

    private OrderKey key; // order id
    private Long size;
    private Double price;
    private Side side;



    public Order(OrderKey key, Long size, Double price, Side side) {
        this.key = key;
        this.size = size;
        this.price = price;
        this.side = side;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return key.equals(order.key) && size.equals(order.size) && price.equals(order.price) && side.equals(order.side);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, size, price, side);
    }
}
