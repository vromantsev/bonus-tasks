package ua.hillel.lesson12.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Please implement this class according to the following steps:
 * 1. Add private field person of type {@link Person}.
 * 2. Add private array of items of type {@link Item}.
 * 3. Add private field totalPrice of type {@link int}.
 * 4. Add 2 constructors - default (no parameters), and a constructor that accepts all arguments
 * 5. Add getters and setters for all fields
 * 6. Add equals, hashCode, and toString methods
 */
public class Order {

    private Person person;
    private Item[] items;
    private int totalPrice;

    public Order() {
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (totalPrice != order.totalPrice) return false;
        if (!Objects.equals(person, order.person)) return false;
        return Arrays.equals(items, order.items);
    }

    @Override
    public int hashCode() {
        int result = person != null ? person.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(items);
        result = 31 * result + totalPrice;
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "person=" + person +
                ", items=" + Arrays.toString(items) +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
