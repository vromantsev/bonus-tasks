package ua.hillel.coffee.board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Queue;

public class CoffeeOrderBoard {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeOrderBoard.class);

    private final Queue<Order> orders;

    public CoffeeOrderBoard(final Queue<Order> orders) {
        this.orders = orders;
    }

    public void add(final String ordererName) {
        Objects.requireNonNull(ordererName, "Parameter [ordererName] must be specified!");
        var orderNumber = OrderNumberGenerator.generateOrderNumber();
        var order = new Order(orderNumber, ordererName);
        this.orders.add(order);
        LOGGER.info("Created an order {} for {}", order.getOrderNumber(), order.getOrdererName());
    }

    public void deliver() {
        if (orders.isEmpty()) {
            throw new IllegalStateException("No orders to deliver!");
        }
        var order = this.orders.poll();
        LOGGER.info("Delivered an order {} for {}", order.getOrderNumber(), order.getOrdererName());
    }

    public void deliver(final long orderNumber) {
        if (orders.isEmpty()) {
            throw new IllegalStateException("No orders to deliver!");
        }
        var order = this.orders.stream()
                .filter(o -> o.getOrderNumber() == orderNumber)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Order %d is not found!".formatted(orderNumber)));
        boolean isRemoved = orders.remove(order);
        if (isRemoved) {
            LOGGER.info("Delivered an order {} for {}", order.getOrderNumber(), order.getOrdererName());
        }
    }

    public void draw() {
        var sb = new StringBuilder();
        sb.append("Num").append(" | ").append("Name").append("\n");
        for (Order order : orders) {
            sb.append(order.getOrderNumber()).append(" | ").append(order.getOrdererName()).append("\n");
        }
        LOGGER.info("Orders dashboard: \n{}", sb);
    }

    private static class OrderNumberGenerator {

        private static long orderNumber;

        static long generateOrderNumber() {
            return ++orderNumber;
        }
    }
}
