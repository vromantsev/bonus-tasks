package ua.hillel.coffee.board;

public class Order {

    private long orderNumber;
    private String ordererName;

    public Order() {
    }

    public Order(long orderNumber, String ordererName) {
        this.orderNumber = orderNumber;
        this.ordererName = ordererName;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrdererName() {
        return ordererName;
    }

    public void setOrdererName(String ordererName) {
        this.ordererName = ordererName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return orderNumber == order.orderNumber;
    }

    @Override
    public int hashCode() {
        return (int) (orderNumber ^ (orderNumber >>> 32));
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", ordererName='" + ordererName + '\'' +
                '}';
    }
}
