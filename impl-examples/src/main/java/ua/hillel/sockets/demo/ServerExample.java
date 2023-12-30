package ua.hillel.sockets.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerExample {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT); // Прослуховування порту 8080
             Socket clientSocket = serverSocket.accept()) { // Очікування на підключення клієнта
            System.out.println("Server is waiting for client...");

            // Отримання введення/виведення для зчитування/запису даних через з'єднання
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            // Отримання даних від клієнта
            String clientData = input.readLine();
            System.out.println("Client sent: " + clientData);

            // Відправка відповіді клієнту
            output.println("Hello, Client!");

        } catch (IOException e) {
            // exception handling
        }
    }
}

