package ua.hillel.netty;

public class NettyServerDemo {

    public static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            new NettyServer(PORT).run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
