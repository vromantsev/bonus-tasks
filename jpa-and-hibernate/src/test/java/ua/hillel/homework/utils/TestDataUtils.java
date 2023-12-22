package ua.hillel.homework.utils;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;
import ua.hillel.homework.entity.Homework;
import ua.hillel.homework.entity.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class TestDataUtils {

    private static final Fairy FAIRY = Fairy.create();

    private TestDataUtils() {
    }

    public static List<Student> getStudents() {
        var students = new ArrayList<Student>();
        var maxSize = 5;
        for (int i = 0; i < maxSize; i++) {
            Person person = FAIRY.person();
            Student student = new Student();
            student.setFirstName(person.getFirstName());
            student.setLastName(person.getLastName());
            student.setEmail(person.getEmail());
            students.add(student);
        }
        return students;
    }

    public static Student createStudent(final String email) {
        Student student = new Student();
        student.setFirstName("Josh");
        student.setLastName("Long");
        student.setEmail(email == null ? FAIRY.person().getEmail() : email);
        Homework homework = new Homework();
        homework.setDescription("test description");
        homework.setDeadline(LocalDate.now());
        student.addHomework(homework);
        return student;
    }
}
