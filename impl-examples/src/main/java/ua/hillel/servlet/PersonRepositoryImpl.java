package ua.hillel.servlet;

import java.util.List;

public class PersonRepositoryImpl implements PersonRepository {

    private final List<Person> persons = List.of(
            new Person("Ivan Ivanov", "ii@gmail.com", 20),
            new Person("John Snow", "js@gmail.com", 25),
            new Person("Jerry Smith", "js@gmail.com", 30)
    );

    @Override
    public List<Person> getPersons() {
        return persons;
    }

    @Override
    public Person findByFullNameAndAge(String fullName, int age) {
        return this.persons.stream()
                .filter(person -> person.getFullName().equalsIgnoreCase(fullName))
                .filter(person -> person.getAge() == age)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Person with fullName %s and age %d not found!".formatted(fullName, age)));
    }

    @Override
    public Person findByEmail(String email) {
        return this.persons.stream()
                .filter(person -> person.getEmail().equals(email))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Person with email %s not found!".formatted(email)));
    }
}
