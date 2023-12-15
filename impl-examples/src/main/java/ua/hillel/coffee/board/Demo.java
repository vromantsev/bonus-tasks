package ua.hillel.coffee.board;

import java.util.LinkedList;

public class Demo {

    private static final String[] NAMES = {"Alen", "Yoda", "Obi-Van", "Anakin Skywalker"};

    public static void main(String[] args) {
        var coffeeBoard = new CoffeeOrderBoard(new LinkedList<>());
        var visitors = 4;
        var idx = 0;

        for (int i = 0; i < visitors; i++) {
            coffeeBoard.add(NAMES[idx++]);
        }

        coffeeBoard.draw();

        coffeeBoard.deliver();

        coffeeBoard.draw();

        coffeeBoard.deliver(4);

        coffeeBoard.draw();
    }
}
