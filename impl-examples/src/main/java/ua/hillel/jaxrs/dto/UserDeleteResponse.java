package ua.hillel.jaxrs.dto;

import java.util.UUID;

public record UserDeleteResponse(String message, boolean isUserDeleted) {

    public static final String SUCCESS_MESSAGE = "User with id %s deleted successfully!";
    public static final String FAILURE_MESSAGE = "User with id %s is not deleted!";

    public static UserDeleteResponse of(UUID id, boolean isUserDeleted) {
        String message = FAILURE_MESSAGE.formatted(id);
        if (isUserDeleted) {
            message = SUCCESS_MESSAGE.formatted(id);
        }
        return new UserDeleteResponse(message, isUserDeleted);
    }
}
