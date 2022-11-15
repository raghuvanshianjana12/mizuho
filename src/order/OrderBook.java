package com.order;


import java.util.*;

public class OrderBook {

    private SortedMap<Double, Map<OrderKey, Order>> bids = new TreeMap<>(Comparator.reverseOrder());
    private SortedMap<Double, Map<OrderKey, Order>> asks = new TreeMap<>();
    private Map<OrderKey, Order> byKey = new HashMap<>(); // All ask bid

    public void addOrder(Order order) {
        OrderKey key = order.getKey();
        Order existingOrder = byKey.get(key);
        Map<Double, Map<OrderKey, Order>> line = order.getSide() == Side.BID ? bids : asks;
        if (existingOrder != null) {
            removeOrder(key);
        }
        byKey.put(key, order);
        Double price = order.getPrice();
        Map<OrderKey, Order> byPrice = line.getOrDefault(price, new TreeMap<>());
        byPrice.put(key, order);
        line.put(price, byPrice);
    }

    public Order removeOrder(OrderKey orderKey) {
        Order existingOrder = byKey.get(orderKey);

        if (existingOrder != null) {
            Map<Double, Map<OrderKey, Order>> line = existingOrder.getSide() == Side.BID ? bids : asks;
            Order orderRemoved = byKey.remove(orderKey);
            Double price = orderRemoved.getPrice();
            Map<OrderKey, Order> byPrice = line.get(price);
            byPrice.remove(orderRemoved.getKey());
            if (byPrice.isEmpty())
                line.remove(price);
            return existingOrder;
        }
        throw new RuntimeException("removeOrder :: No such value present OrderKey " + orderKey.getOrderId());
    }

    public Order updateOrder(OrderKey orderKey, Long size) {
        Order existingOrder = byKey.get(orderKey);

        if (existingOrder != null) {
            Map<Double, Map<OrderKey, Order>> line = existingOrder.getSide() == Side.BID ? bids : asks;
            existingOrder.setSize(size);
            Double price = existingOrder.getPrice();
            Map<OrderKey, Order> byPrice = line.get(price);
            Order order = byPrice.get(existingOrder.getKey());
            order.setSize(size);
            return existingOrder;
        }
        throw new RuntimeException("updateOrder :: No such value present OrderKey " + orderKey.getOrderId());
    }

    public Long getTotalSizeBySideAndLevel(Side side, int level) {
        Map<Double, Map<OrderKey, Order>> line = side == Side.BID ? bids : asks;
        Optional<Map<OrderKey, Order>> orderMap = line.values().stream().skip(level - 1).findFirst();
        if (orderMap.isPresent()) {
            return orderMap.get().values().stream().mapToLong(Order::getSize).reduce(0, (s1, s2) -> s1 + s2);
        }
        throw new RuntimeException("getTotalSizeBySideAndLevel :: No such value present Side and Level " + side.name() + " :: " + level);
    }

    public Double getPriceBySideAndLevel(Side side, int level) {
        Map<Double, Map<OrderKey, Order>> line = side == Side.BID ? bids : asks;
        Optional<Double> price = line.keySet().stream().skip(level - 1).findFirst();
        if (price.isPresent()) {
            return price.get();
        }
        throw new RuntimeException("getPriceBySideAndLevel :: No such value present Side and Level " + side.name() + " :: " + level);
    }

    public Double getBestPrice(Side side) {
        SortedMap<Double, Map<OrderKey, Order>> line = side == Side.BID ? bids : asks;
        return line.firstKey();
    }
}
