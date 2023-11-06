package ua.hillel.lesson12;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.hillel.lesson12.model.Item;
import ua.hillel.lesson12.model.Order;
import ua.hillel.lesson12.model.Person;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EncapsulationDemoServiceTest {

    private static EncapsulationDemoService service;

    @BeforeAll
    public static void setup() {
        service = new EncapsulationDemoService();
    }

    @AfterAll
    public static void tearDown() {
        service = null;
    }

    @DisplayName("Test checks whether createOrderInfo() method produces correct result.")
    @Test
    public void createOrderInfoProducesCorrectResult() {
        // given
        TestPerson person = new TestPerson("Ilya Ivanov", "i.ivanov@gmail.com");
        TestItem iphone = new TestItem("iPhone 15 Pro Max", 1500);
        TestItem airPods = new TestItem("Air Pods", 200);
        TestOrder order = new TestOrder();
        order.setPerson(person);
        order.setItems(new Item[]{iphone, airPods});
        String expected = """
                User Ilya Ivanov ordered the following items:
                - %s costs %d$
                - %s costs %d$
                Total price is 1700$"""
                .formatted(iphone.getName(), iphone.getPrice(), airPods.getName(), airPods.getPrice());

        // then
        String orderInfo = service.createOrderInfo(order);

        // assertions & verification
        assertNotNull(orderInfo, "Order info must not be null!");
        assertEquals(expected, orderInfo, "Order info is not the same as expected!");
    }

    @DisplayName("Test checks whether calculateTotalPrice() method produces correct result.")
    @Test
    public void calculateTotalPriceProducesCorrectResult() {
        // given
        TestPerson person = new TestPerson("Ilya Ivanov", "i.ivanov@gmail.com");
        TestItem iphone = new TestItem("iPhone 15 Pro Max", 1500);
        TestItem airPods = new TestItem("Air Pods", 200);
        TestOrder order = new TestOrder();
        order.setPerson(person);
        order.setItems(new Item[]{iphone, airPods});
        int expectedTotalPrice = 1700;

        // then
        int totalPrice = service.calculateTotalPrice(order);

        // assertions & verification
        assertEquals(expectedTotalPrice, totalPrice, "Order's total balance is not correct!");
    }

    public static class TestItem extends Item {

        private String name;
        private int price;

        public TestItem() {
        }

        public TestItem(String name, int price) {
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

            TestItem item = (TestItem) o;

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

    public static class TestOrder extends Order {

        private Person person;
        private Item[] items;
        private int totalPrice;

        public TestOrder() {
        }

        public TestOrder(Person person, Item[] items, int totalPrice) {
            this.person = person;
            this.items = items;
            this.totalPrice = totalPrice;
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
            if (!super.equals(o)) return false;

            TestOrder testOrder = (TestOrder) o;

            if (totalPrice != testOrder.totalPrice) return false;
            if (!Objects.equals(person, testOrder.person)) return false;
            return Arrays.equals(items, testOrder.items);
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (person != null ? person.hashCode() : 0);
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

    public static class TestPerson extends Person {

        private String fullName;
        private String email;

        public TestPerson() {
        }

        public TestPerson(String fullName, String email) {
            this.fullName = fullName;
            this.email = email;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestPerson person = (TestPerson) o;

            if (!Objects.equals(fullName, person.fullName)) return false;
            return Objects.equals(email, person.email);
        }

        @Override
        public int hashCode() {
            int result = fullName != null ? fullName.hashCode() : 0;
            result = 31 * result + (email != null ? email.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "fullName='" + fullName + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
}
