package ua.hillel.jaxrs.utils;

import java.util.UUID;

public final class IdGeneratorUtils {

    public IdGeneratorUtils() {
    }

    public static UUID generateId() {
        return UUID.randomUUID();
    }
}
