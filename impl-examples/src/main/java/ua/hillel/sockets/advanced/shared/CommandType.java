package ua.hillel.sockets.advanced.shared;

public enum CommandType {

    EXIT("exit"),
    NAME("Name"),
    NOT_FOUND("Not found");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
