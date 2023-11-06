package ua.hillel.lesson12;

import ua.hillel.lesson12.model.Item;
import ua.hillel.lesson12.model.Order;
import ua.hillel.lesson12.model.Person;

public class EncapsulationDemoService {

    /**
     * This method is responsible for providing an information about the user's order. It returns a string
     * representation with main order information. The output example is shown below.
     *
     * Expected output:
     * User Ilya Ivanov ordered the following items:
     * - phone costs 1500$
     * - optical mouse costs 25$
     * Total price is 1525$
     *
     * PLEASE NOTE that this output is just an example of how it should look like. The data (names, prices etc.)
     * might differ.
     *
     *  For more details see:
     * {@link Order}
     * {@link Item}
     * {@link Person}
     *
     * @param order order
     */
    public String createOrderInfo(Order order) {
        var sb = new StringBuilder();
        sb.append("User ").append(order.getPerson().getFullName()).append(" ordered the following items:\n");
        for (Item item : order.getItems()) {
            sb.append("- ").append(item.getName()).append(" costs ").append(item.getPrice()).append("$\n");
        }
        sb.append("Total price is ").append(calculateTotalPrice(order)).append("$");
        return sb.toString();
    }

    /**
     * Method calculates the total price of an order. The total price should be calculated
     * using the price of each {@link Item} in the order.
     *
     * For more details see:
     * {@link Order}
     * {@link Item}
     *
     * @param order order
     * @return order's total price
     */
    public int calculateTotalPrice(Order order) {
        int totalPrice = order.getTotalPrice();
        for (Item item : order.getItems()) {
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }
}
