package ua.hillel.servlet;

import java.util.Objects;

public class Person {

    private String fullName;
    private String email;
    private int age;

    public Person() {
    }

    public Person(String fullName, String email, int age) {
        this.fullName = fullName;
        this.email = email;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (age != person.age) return false;
        if (!Objects.equals(fullName, person.fullName)) return false;
        return Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        int result = fullName != null ? fullName.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + age;
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }

    public String asJson() {
        return new StringBuilder()
                .append("{\n")
                .append("    \"fullName\": \"").append(fullName).append("\",\n")
                .append("    \"email\": \"").append(email).append("\",\n")
                .append("    \"age\": ").append(age).append("\n")
                .append("}")
                .toString();
    }
}
