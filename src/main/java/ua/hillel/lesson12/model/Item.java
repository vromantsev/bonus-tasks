package ua.hillel.lesson12.model;

import java.util.Objects;

/**
 * Please implement this class according to the following steps:
 * 1. Add private field name of type {@link String}.
 * 2. Add private field price of type {@link int}.
 * 3. Add 2 constructors - default (no parameters), and a constructor that accepts all arguments
 * 4. Add getters and setters for all fields
 * 5. Add equals, hashCode, and toString methods
 */
public class Item {

    private String name;
    private int price;

    public Item() {
    }

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (Double.compare(price, item.price) != 0) return false;
        return Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return name + " costs " + price + "$";
    }
}
