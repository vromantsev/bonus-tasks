package ua.hillel.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientExample {

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT)) { // Підключення до сервера на localhost, порт 8080

            // Отримання введення/виведення для зчитування/запису даних через з'єднання
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // Відправка даних на сервер
            output.println("Hello, Server!");

            // Отримання відповіді від сервера
            String response = input.readLine();
            System.out.println("Server response: " + response);
        } catch (IOException e) {
            // exception handling
        }
    }
}

