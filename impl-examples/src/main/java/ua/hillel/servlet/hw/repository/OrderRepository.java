package ua.hillel.servlet.hw.repository;

import ua.hillel.servlet.hw.entity.Order;

import java.util.concurrent.ThreadLocalRandom;

public interface OrderRepository {

    Order create(final Order order);

    Order findById(final Long id);

    Order update(final Order order);

    boolean deleteById(final Long id);

    default Long generateId() {
        return ThreadLocalRandom.current().nextLong(1, 1000_000);
    }
}
