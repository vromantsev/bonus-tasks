package ua.hillel.servlet;

import java.util.List;

public interface PersonRepository {

    List<Person> getPersons();

    Person findByFullNameAndAge(String fullName, int age);

    Person findByEmail(String email);

}
