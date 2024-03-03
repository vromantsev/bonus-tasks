package ua.hillel.servlet.hw.repository;

import ua.hillel.servlet.hw.entity.Order;
import ua.hillel.servlet.hw.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrderRepositoryImpl implements OrderRepository {

    private static final Map<Long, Order> ID_TO_ORDER_MAP = new HashMap<>();

    @Override
    public Order create(final Order order) {
        Objects.requireNonNull(order, "Parameter [order] must not be null!");
        if (order.hasId()) {
            throw new IllegalArgumentException("New order should not have an id!");
        }
        var id = this.generateId();
        order.setId(id);
        if (order.getCreatedAt() == null) {
            order.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));
        }
        List<Product> products = order.getProducts();
        products.forEach(p -> p.setId(generateId()));
        BigDecimal cost;
        if (products.isEmpty()) {
            cost = BigDecimal.ZERO;
        } else {
            cost = calculateTotalOrderCost(products);
        }
        order.setCost(cost);
        ID_TO_ORDER_MAP.put(id, order);
        return order;
    }

    @Override
    public Order findById(final Long id) {
        Objects.requireNonNull(id, "Parameter [id] must not be null!");
        if (ID_TO_ORDER_MAP.containsKey(id)) {
            return ID_TO_ORDER_MAP.get(id);
        }
        throw new IllegalArgumentException("Order with id=%d not found!".formatted(id));
    }

    @Override
    public Order update(final Order order) {
        Objects.requireNonNull(order, "Parameter [order] must not be null!");
        if (!order.hasId()) {
            throw new IllegalArgumentException("Order must have and id for update operation!");
        }
        return updateOrder(order);
    }

    private Order updateOrder(final Order order) {
        Long orderId = order.getId();
        Order updatable = ID_TO_ORDER_MAP.get(orderId);
        List<Product> newProducts = new ArrayList<>();
        List<Product> currentProducts = updatable.getProducts();
        for (Product product : currentProducts) {
            Product productFromOrder = getProductFromOrder(product.getId(), order);
            if (productFromOrder == null) {
                newProducts.add(product);
                continue;
            }
            if (product.getCost().compareTo(productFromOrder.getCost()) != 0) {
                product.setCost(productFromOrder.getCost());
            }
            if (!product.getName().equals(productFromOrder.getName())) {
                product.setName(productFromOrder.getName());
            }
            newProducts.add(product);
        }
        updatable.setProducts(newProducts);
        order.setCost(calculateTotalOrderCost(newProducts));
        ID_TO_ORDER_MAP.put(orderId, order);
        return order;
    }

    private BigDecimal calculateTotalOrderCost(final List<Product> products) {
        return products.stream()
                .map(Product::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Product getProductFromOrder(final Long id, final Order order) {
        return order.getProducts().stream()
                .filter(p -> p.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Order has no product with id=%d".formatted(id)));
    }

    @Override
    public boolean deleteById(final Long id) {
        Objects.requireNonNull(id, "Parameter [id] must not be null!");
        if (ID_TO_ORDER_MAP.containsKey(id)) {
            ID_TO_ORDER_MAP.remove(id);
            return true;
        }
        return false;
    }
}
