package ua.hillel.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Contact implements Serializable {

    private String name;
    private String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (!Objects.equals(name, contact.name)) return false;
        return Objects.equals(phoneNumber, contact.phoneNumber);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public static void main(String[] args) {
        // ім'я файлу
        String fileName = "contacts.txt";

        // створення списку контактів
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("John Doe", "123-456-7890"));
        contacts.add(new Contact("Jane Doe", "987-654-3210"));

        // запис об'єктів у файл з використанням серіалізації
        serialize(contacts, fileName);

        // зчитування об'єктів з файлу з використанням серіалізації
        List<Contact> contactList = deserialize(fileName);

        // друкуємо зчитані контакти в консоль
        System.out.println(contactList);
    }

    @SuppressWarnings("unchecked")
    private static List<Contact> deserialize(final String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<Contact>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void serialize(final List<Contact> contacts, final String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(contacts);
            System.out.printf("Contacts saved to %s%n", fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

