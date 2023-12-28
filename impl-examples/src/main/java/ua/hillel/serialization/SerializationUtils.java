package ua.hillel.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

public final class SerializationUtils {

    private SerializationUtils() {
    }

    public static void serializeToFile(final Person person, final String filename) {
        Objects.requireNonNull(filename, "Parameter [person] must not be null!");
        Objects.requireNonNull(filename, "Parameter [filename] must not be null!");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(person);
            System.out.printf("Serialized person object and stored its representation in a file '%s'%n", filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Person deserializeFromFile(final String filename) {
        Objects.requireNonNull(filename, "Parameter [filename] must not be null!");
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(filename))) {
            return (Person) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
