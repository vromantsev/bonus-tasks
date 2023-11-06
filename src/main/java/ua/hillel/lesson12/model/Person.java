package ua.hillel.lesson12.model;

import java.util.Objects;

/**
 * Please implement this class according to the following steps:
 * 1. Add private field fullName of type {@link String}.
 * 2. Add private field email of type {@link String}.
 * 3. Add 2 constructors - default (no parameters), and a constructor that accepts all arguments
 * 4. Add getters and setters for all fields
 * 5. Add equals, hashCode, and toString methods
 */
public class Person {

    private String fullName;
    private String email;

    public Person(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!Objects.equals(fullName, person.fullName)) return false;
        return Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        int result = fullName != null ? fullName.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
