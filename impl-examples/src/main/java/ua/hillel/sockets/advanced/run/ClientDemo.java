package ua.hillel.sockets.advanced.run;

import ua.hillel.sockets.advanced.client.Client;
import ua.hillel.sockets.advanced.shared.CommonUtils;

    public class ClientDemo {

        public static void main(String[] args) {
            var clients = 10;
            for (int i = 0; i < clients; i++) {
                Client.connectToServer();
            }

            CommonUtils.getClientNames()
                    .forEach(clientName -> Client.sendCommandToServer("exit %s".formatted(clientName)));
        }
    }
