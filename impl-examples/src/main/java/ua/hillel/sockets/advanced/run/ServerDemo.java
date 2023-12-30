package ua.hillel.sockets.advanced.run;

import ua.hillel.sockets.advanced.server.Server;

public class ServerDemo {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        Server.start(PORT);
    }
}
