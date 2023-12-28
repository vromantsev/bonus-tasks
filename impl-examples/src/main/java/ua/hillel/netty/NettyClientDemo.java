package ua.hillel.netty;

public class NettyClientDemo {

    public static final int PORT = 8080;
    public static final String HOST = "localhost";

    public static void main(String[] args) {
        try {
            NettyClient client = new NettyClient(HOST, PORT);
            client.run("Hello Netty Server!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
